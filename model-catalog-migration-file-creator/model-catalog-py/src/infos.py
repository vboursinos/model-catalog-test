#!/usr/bin/env python3.7
# encoding: utf-8
# ───────────────────────────────── imports ────────────────────────────────── #
# Standard Library
from typing import Dict, List

# Dependencies
from evoml_api_models import MlTask
from metaml import ModelTag
from metaml.factory import factory
from metaml.metadata.names import ModelNameType, ClassifierName, ForecasterName, RegressorName
import metaml
from models import (
    MetamlLiteParameters,
    MetamlParamSettings,
    ModelInfoList,
    ModelInfo,
    ModelName,
    ThanosMetric,
    Metric,
    MetamlInputParameter, Parameter, Domain, Range, IntegerSet, FloatSet, Interval, CategoricalSet, Constraint, Item,
    Distribution, Metadata, Support
)
import config

# -------------------------------- constants --------------------------------- #
SLUGS_HOTFIX: Dict[Metric, ThanosMetric] = {'roc-auc': 'roc'}
"""Map of metaml's metrics names to Thanos loss function names (named
slugs, i.e. kebab-case version of a loss function name, as opposed to a display
name).
They're exactly the same most of the time, this maps the few exceptions.
"""


def extract_integer_set(domain_data):
    if any(key in domain_data for key in ["integer_set", "float_set", "categorical_set"]):
        if domain_data.get("integer_set"):
            ranges = [Range(start=range["start"], stop=range["stop"]) for range in domain_data["integer_set"]["ranges"]]
            return IntegerSet(ranges=ranges)
    else:
        if domain_data.get("ranges"):
            ranges = [Range(start=range["start"], stop=range["stop"]) for range in domain_data["ranges"]]
            return IntegerSet(ranges=ranges)
    return None


def extract_float_set(domain_data):
    if any(key in domain_data for key in ["integer_set", "float_set", "categorical_set"]):
        if domain_data.get("float_set"):
            intervals = [Interval(left=interval["left"].value, right=interval["right"].value,
                                  lower=interval["lower"], upper=interval["upper"])
                         for interval in domain_data["float_set"]["intervals"]]
            return FloatSet(intervals=intervals)
    else:
        if domain_data.get("intervals"):
            intervals = [Interval(left=interval["left"].value, right=interval["right"].value,
                                  lower=interval["lower"], upper=interval["upper"])
                         for interval in domain_data["intervals"]]
            return FloatSet(intervals=intervals)
    return None


def extract_categorical_set(domain_data):
    if any(key in domain_data for key in ["integer_set", "float_set", "categorical_set"]):
        if domain_data.get("categorical_set"):
            categories = domain_data["categorical_set"]["categories"]
            return CategoricalSet(categories=categories)
    else:
        if domain_data.get("categories"):
            categories = [category for category in domain_data["categories"]]
            return CategoricalSet(categories=categories)
    return None


def extract_distribution(input_param, domain: Domain) -> Distribution:
    distribution: Distribution = Distribution()
    if "distribution" in input_param:
        if domain.integerSet:
            distribution.integerDistribution = input_param["distribution"].value
        elif domain.floatSet:
            distribution.floatDistribution = input_param["distribution"].value
    elif "integer_distribution" or "float_distribution" or "categorical_distribution" in input_param:
        if domain.integerSet:
            distribution.integerDistribution = input_param["integer_distribution"].value
        if domain.floatSet:
            distribution.floatDistribution = input_param["float_distribution"].value
    else:
        distribution = None
    return distribution


def get_model_parameters(parameters: MetamlParamSettings) -> List[Parameter]:
    """Extracts the parameters from the Metaml parameters dictionary and
    converts them to the Parameter pydantic model."""
    parameter_list = []
    for input_param in parameters:
        domain_data = input_param["domain"]
        integer_set = extract_integer_set(domain_data)
        float_set = extract_float_set(domain_data)
        categorical_set = extract_categorical_set(domain_data)

        domain = Domain(integerSet=integer_set, categoricalSet=categorical_set, floatSet=float_set)

        parameter = Parameter(name=input_param["name"],
                              label=input_param["label"],
                              description=input_param["description"],
                              enabled=input_param["enabled"],
                              fixedValue=input_param["fixedValue"],
                              constraint=input_param["constraint"],
                              constraintInformation=input_param["constraintInformation"],
                              defaultValue=input_param["default_value"],
                              domain=domain,
                              distribution=extract_distribution(input_param, domain), )
        parameter_list.append(parameter)

        # parameters_to_lite(input_param, parameter)
    return parameter_list


def get_model_constraint_edges(constraints: MetamlParamSettings) -> List[Constraint]:
    constraint_list = []
    for constraint_tuple in constraints:
        mapping = constraint_tuple[2]
        items = []
        for pair in mapping["items"]:
            source_integer_set = extract_integer_set(pair["source"])
            source_float_set = extract_float_set(pair["source"])
            source_categorical_set = extract_categorical_set(pair["source"])

            target_integer_set = extract_integer_set(pair["target"])
            target_float_set = extract_float_set(pair["target"])
            target_categorical_set = extract_categorical_set(pair["target"])

            source_domain = Domain(integerSet=source_integer_set, categoricalSet=source_categorical_set,
                                   floatSet=source_float_set)
            target_domain = Domain(integerSet=target_integer_set, categoricalSet=target_categorical_set,
                                   floatSet=target_float_set)
            item: Item = Item(source=source_domain, target=target_domain)
            items.append(item)
        constraint = Constraint(source=constraint_tuple[0], target=constraint_tuple[1], mapping=items)
        constraint_list.append(constraint)
    return constraint_list


def get_model_metadata(parameters: MetamlParamSettings) -> Metadata:
    old_metadata = parameters["metadata"]
    support = Support(
        probabilities=old_metadata["supports"]["probabilities"],
        decisionTree=old_metadata["supports"]["decisionTree"],
        featureImportance=old_metadata["supports"]["feature_importances"],
    )
    metadata = Metadata(
        model=old_metadata["model_name"],
        modelDescription=old_metadata["description"].replace("\n", "").replace("''", "'"),
        modelType=old_metadata["model_type"],
        advantages=old_metadata["advantages"],
        disadvantages=old_metadata["disadvantages"],
        prime=old_metadata["prime"],
        displayName=old_metadata["display_name"],
        supports=support,
        structure=old_metadata["structure"],
        dependencyGroup=old_metadata["dependency_group"].value,
    )
    return metadata


def parameters_to_lite(
        model_name: ModelName, parameters: MetamlParamSettings
) -> MetamlLiteParameters:
    """Does minor modifications to a model parameter to inject fields or make
    changes that do not fall into Metaml's scope, i.e. very Thanos specific.

    Injects four fields:
    - inputParameters[].fixedValue (→ False)
    - metadata.structure (→ dynamic | base)
    """
    # Insert keys needed on this layer, but not part of Metaml

    # Adds a field, 'fixedValue → False' to all `inputParameters` sub-dict of
    # the given parameters dictionary.
    # This addition is needed because the field `fixedValue` does not exist in
    # metaml, but is needed for user interaction.
    for input_param in parameters.get("parameters", []):
        input_param["fixedValue"] = False

    # Extends the dictionary `metadata.supports` to include the fields of the
    # `ModelSupport` pydantic model. Existing fields are "support" covered by
    # metaml.
    for key, value in config.get_model_support(model_name).dict().items():
        parameters["metadata"]["supports"][key] = value

    # Adds a field, 'structure → Structure' to all `inputParameters.metadata`
    # sub-dict of the given parameters dictionary.
    # This addition is needed because the field `structure` does not exist in
    # metaml, but is needed for conditional behaviour on some models.
    is_dynamic = "stacking" in model_name or "vote" in model_name
    structure = "dynamic" if is_dynamic else "base"
    parameters["metadata"]["structure"] = structure

    parameters["inputParameters"] = sort_input_parameters(parameters)

    return parameters


def sort_input_parameters(parameters: MetamlParamSettings) -> List[MetamlInputParameter]:
    """
    Sort the inputParameters list based on whether the parameter appears in the prime list,
    whether the parameter is enabled, and finally in alphabetical order of the parameter names.
    Purpose is to have more relevant parameters appear first on the UI.

    Args:
        parameters (MetamlParamSettings): A dictionary containing the inputParameters list and metadata.

    Returns:
        List[MetamlInputParameter]: A sorted list of input parameters.
    """
    input_parameters: List[MetamlInputParameter] = parameters["inputParameters"]
    prime = parameters["metadata"]["prime"]

    def sort_key(input_parameter: MetamlInputParameter) -> tuple:
        in_prime = input_parameter["parameterName"] in prime
        return (not in_prime, not input_parameter["enabled"], input_parameter["parameterName"])

    sorted_input_parameters = sorted(input_parameters, key=sort_key)
    return sorted_input_parameters


# ------------------------------ utils methods ------------------------------- #
def get_model_names(ml_task: MlTask) -> List[ModelName]:
    """Returns the list of model names for a given machine learning task"""
    # from metaml import ModelFactory

    if ml_task == MlTask.regression:
        return factory.get_model_list(contains=ModelTag.regressor)
    if ml_task == MlTask.classification:
        return factory.get_model_list(contains=ModelTag.classifier)
    if ml_task == MlTask.forecasting:
        return factory.get_model_list(contains=ModelTag.forecaster)
    raise NotImplementedError(f"{ml_task} not supported")


def get_incompatible_metrics(model_name: str, parameters: MetamlParamSettings) -> List[ThanosMetric]:
    """
    Returns the list of metrics that a model doesn't support add
    additional switches as they come to light
    """
    incompatible_metrics = []

    # log-loss and classification-roc require predict proba
    if not parameters["metadata"]["supports"]["probabilities"]:
        incompatible_metrics += ["classification-log-loss", "classification-roc"]

    return incompatible_metrics


def is_blacklisted_model(parameter: MetamlParamSettings) -> bool:
    if "experimental" in parameter["metadata"]["tags"]:
        return True

    return False


# ------------------------------- main method -------------------------------- #


def get_model_infos(ml_task: MlTask) -> ModelInfoList:
    """Build the entire list of information about models supported by metaml,
    given a machine learning task (classificaiton, regression).

    This is sent and read by Thanos. We need to handle a few
    translations/inconsistencies to adapt MetaML's concept space to Thanos'
    concept space.
    """
    # from metaml import ModelParam
    # metaml_param = ModelParam()

    models = []

    # Get parameters for each model
    for model_name in get_model_names(ml_task):

        # Fetch the parameter json defined by metaml
        try:
            params: MetamlParamSettings = factory.get_simple_param_by_name(model_name).dict()
        except Exception:
            ...
        else:
            # Insert keys needed on this layer, but not part of Metaml
            lite_params = parameters_to_lite(model_name, params)
            # Build a list of incompatible metrics - empty if no problematic metrics
            incompatible_metrics = get_incompatible_metrics(model_name, params)

            metaml_blacklisted = is_blacklisted_model(params)


            # Add the model to the list
            models.append(
                ModelInfo(
                    name=model_name,
                    mlTask=ml_task,
                    incompatibleMetrics=incompatible_metrics,
                    blackListed=metaml_blacklisted,
                    hyperParameters=get_model_parameters(lite_params.get("parameters", [])),
                    constraintEdges=get_model_constraint_edges(params.get("constraints", [])),
                    metadata=get_model_metadata(params)
                )
            )
            print(len(lite_params["parameters"]))
            print(len(get_model_parameters(params.get("parameters", []))))
            assert len(lite_params["parameters"]) == len(get_model_parameters(params.get("parameters", [])))
    return ModelInfoList(models=models)
