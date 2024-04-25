from typing import List, Dict, Any, Optional
from enum import Enum

from pydantic import BaseModel

from evoml_api_models import MlTask

# ─────────────────────────────── type aliases ─────────────────────────────── #
ThanosMetric = str
"""Kebab case unique name for metrics as defined in Thanos"""
MetamlMetric = str
"""Chaotic casing for metric as defined in Metaml"""
Metric = str
"""Kebab case version of the MetamlMetric"""

ModelName = str
"""Snake case unique name for each Machine Learning model"""

MetamlParamSettings = dict
"""Model's parameter settings and metadata as defined in the ParamSettings pydantic model in Metaml"""
MetamlInputParameter = dict
"""Settings for a single input parameter as defined in the InputParameter pydantic model in Metaml"""
MetamlLiteParameters = dict
"""Model's parameter including MetamlLite's patches to inject some fields that
do not belong to Metaml's scope
"""


# ---------------------------------------------------------------------------- #
class Group(str, Enum):
    """Default groups for providing the user with an easy selection of models"""
    fast = "fast"
    explainable = "explainable"
    advanced = "advanced"


class Interval(BaseModel):
    left: bool
    right: bool
    lower: float
    upper: float


class Range(BaseModel):
    start: int
    stop: int


class IntegerSet(BaseModel):
    ranges: Optional[List[Range]]


class CategoricalSet(BaseModel):
    categories: Optional[List[str]]


class FloatSet(BaseModel):
    intervals: Optional[List[Interval]]


class Domain(BaseModel):
    integerSet: Optional[IntegerSet]
    categoricalSet: Optional[CategoricalSet]
    floatSet: Optional[FloatSet]


class Distribution(BaseModel):
    categoricalDistribution: Optional[str]
    floatDistribution: Optional[str]
    integerDistribution: Optional[str]


class Parameter(BaseModel):
    name: str
    label: str
    description: str
    domain: Domain
    enabled: bool
    defaultValue: Any
    constraint: bool
    constraintInformation: Optional[str]
    distribution: Optional[Distribution]
    fixedValue: bool


class DefaultCategory(BaseModel):
    name: str
    models: List[str]


class Item(BaseModel):
    source: Domain
    target: Domain


class Constraint(BaseModel):
    source: str
    target: str
    mapping: Optional[List[Item]]


class Support(BaseModel):
    probabilities: bool
    decisionTree: bool
    featureImportance: bool


class Metadata(BaseModel):
    model: str
    modelDescription: str
    modelType: List[str]
    advantages: List[str]
    disadvantages: List[str]
    prime: List[str]
    displayName: str
    supports: Support
    structure: str
    dependencyGroup: Optional[str]


class ModelInfo(BaseModel):
    name: str
    mlTask: MlTask
    # parameters: Dict[str, Any]
    incompatibleMetrics: List[str] = []
    groups: List[Group] = []
    blackListed: bool
    hyperParameters: List[Parameter]
    constraintEdges: List[Constraint]
    metadata: Metadata


class ModelInfoList(BaseModel):
    models: List[ModelInfo]
