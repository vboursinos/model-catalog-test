package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.parameters;

import static ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.parameters.ParameterTablesBuilder.*;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.*;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.parameters.typeParameters.ParameterStrategy;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.parameters.typeParameters.TypeParameterStrategyFactoryImpl;
import ai.turintech.modelcatalog.migrationfilescreator.model.Domain;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.TableCreatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertParametersTablesImpl extends TableCreatorHelper
    implements InsertParametersTables {

  @Autowired private TypeParameterStrategyFactoryImpl typeParameterStrategyFactory;
  @Autowired private CompareParametersService compareParametersService;

  public String compareTypeParameterAndInsert(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {

    StringBuilder sb = new StringBuilder();
    ParameterStrategy strategy =
        typeParameterStrategyFactory.getParameterStrategy(
            parameterTypeDistribution.getParameterType());

    if (strategy != null) {
      sb.append(strategy.appendParameterSQL(modelName, parameter, parameterTypeDistribution));
    }

    return sb.toString();
  }

  public String updateTypeParameter(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {
    StringBuilder sb = new StringBuilder();

    ParameterStrategy strategy =
        typeParameterStrategyFactory.getParameterStrategy(
            parameterTypeDistribution.getParameterType());

    if (strategy != null) {
      sb.append(
          strategy.appendUpdateTypeParameterSQL(modelName, parameter, parameterTypeDistribution));
    }
    return sb.toString();
  }

  public static List<ParameterTypeDistribution> getParameterTypeDistributionList(
      HyperParameter parameter) {
    List<ParameterTypeDistribution> parameterTypes = new ArrayList<>();
    Domain domain = parameter.getDomain();
    Object defaultValue = null;

    if (domain.getCategoricalSet() != null
        && !domain.getCategoricalSet().getCategories().isEmpty()) {
      String distributionType =
          (parameter.getDefaultValue().equals(true) || parameter.getDefaultValue().equals(false))
              ? "boolean"
              : "categorical";
      if (parameter.getDefaultValue() instanceof String
          || parameter.getDefaultValue() instanceof Boolean) {
        defaultValue = parameter.getDefaultValue();
      }
      parameterTypes.add(
          new ParameterTypeDistribution(
              distributionType,
              "uniform",
              parameter.getName(),
              domain.getCategoricalSet(),
              defaultValue));
    }
    defaultValue = null;
    if (domain.getFloatSet() != null && !domain.getFloatSet().getIntervals().isEmpty()) {
      if (parameter.getDefaultValue() instanceof Double
          || parameter.getDefaultValue() instanceof Float) {
        defaultValue = parameter.getDefaultValue();
      }
      parameterTypes.add(
          new ParameterTypeDistribution(
              "float",
              parameter.getDistribution().getFloatDistribution(),
              parameter.getName(),
              domain.getFloatSet(),
              defaultValue));
    }
    defaultValue = null;
    if (domain.getIntegerSet() != null && !domain.getIntegerSet().getRanges().isEmpty()) {
      if (parameter.getDefaultValue() instanceof Integer) {
        defaultValue = parameter.getDefaultValue();
      }
      parameterTypes.add(
          new ParameterTypeDistribution(
              "integer",
              parameter.getDistribution().getIntegerDistribution(),
              parameter.getName(),
              domain.getIntegerSet(),
              defaultValue));
    }
    return parameterTypes;
  }

  public String insertIntoParameterAndParameterValue(
      List<HyperParameter> orderedParameters, String name, List<ModelDTO> models) {

    StringBuilder sb = new StringBuilder();

    ModelDTO dbModel = findModelByName(models, name);

    if (dbModel != null) {
      insertParametersForModel(orderedParameters, sb, dbModel, name);
    } else {
      insertParametersWithoutModel(orderedParameters, sb, name);
    }

    return sb.toString();
  }

  private static ModelDTO findModelByName(List<ModelDTO> models, String name) {
    for (ModelDTO dbModel : models) {
      if (dbModel.getName().equals(name)) {
        return dbModel;
      }
    }
    return null;
  }

  private void insertParametersForModel(
      List<HyperParameter> orderedParameters, StringBuilder sb, ModelDTO dbModel, String name) {
    int count = 0;
    for (HyperParameter parameter : orderedParameters) {
      boolean parameterFound = false;
      String description = parameter.getDescription().replace("'", "''");
      count++;

      for (ParameterDTO dbParameter : dbModel.getParameters()) {
        if (parameter.getName().equals(dbParameter.getName())) {
          if (compareParametersService.compareParameterColumns(parameter, dbParameter)) {
            parameterFound = true;
          } else {
            for (ParameterTypeDistribution parameterType :
                InsertParametersTablesImpl.getParameterTypeDistributionList(parameter)) {
              if (compareParametersService.compareTypeParameter(
                  parameter, parameterType, dbParameter)) {
                parameterFound = true;
              }
            }
          }
        }

        if (parameter.getName().equals(dbParameter.getName())) {
          for (ParameterTypeDistribution parameterType :
              InsertParametersTablesImpl.getParameterTypeDistributionList(parameter)) {
            int parameterTypeDefCount = 0;
            boolean definitionFound = false;
            for (ParameterTypeDefinitionDTO dbParameterTypeDefinition :
                dbParameter.getDefinitions()) {
              if (compareParametersService.compareParameterTypeDefinition(
                  parameterType, dbParameterTypeDefinition, dbParameter.getName())) {
                definitionFound = true;
              }
              parameterTypeDefCount++;
            }
            if (!definitionFound) {
              sb.append(
                  buildInsertIntoParameterTypeDefinitionSQL(
                      parameter, name, parameterType, parameterTypeDefCount));
              sb.append(buildRevInfoInsertSQL());
              sb.append(
                  ParameterTablesBuilder.buildInsertParameterTypeDefinitionAuditSQL(
                      parameter.getName(), name, parameterType.getParameterType(), 0));
              sb.append(compareTypeParameterAndInsert(parameter, parameterType, name));
            } else {
              if (!compareParametersService.compareTypeParameter(
                  parameter, parameterType, dbParameter)) {
                sb.append(updateTypeParameter(parameter, parameterType, name));
              }
            }
          }
        }
      }

      if (!parameterFound) {
        insertParameterSQLAndAudit(parameter, description, count, name, sb);
      }
    }
  }

  private void insertParametersWithoutModel(
      List<HyperParameter> orderedParameters, StringBuilder sb, String name) {
    int count = 0;
    for (HyperParameter parameter : orderedParameters) {
      String description = parameter.getDescription().replace("'", "''");
      count++;
      insertParameterSQLAndAudit(parameter, description, count, name, sb);
    }
  }

  private void insertParameterSQLAndAudit(
      HyperParameter parameter, String description, int count, String name, StringBuilder sb) {
    sb.append(insertParameterSQL(parameter, description, count, name));
    sb.append(buildRevInfoInsertSQL());
    sb.append(ParameterTablesBuilder.buildInsertParameterAuditSQL(parameter.getName(), name, 0));
    int parameterTypeDefCount = 0;
    for (ParameterTypeDistribution parameterType :
        InsertParametersTablesImpl.getParameterTypeDistributionList(parameter)) {
      sb.append(
          buildInsertIntoParameterTypeDefinitionSQL(
              parameter, name, parameterType, parameterTypeDefCount));
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          ParameterTablesBuilder.buildInsertParameterTypeDefinitionAuditSQL(
              parameter.getName(), name, parameterType.getParameterType(), 0));
      parameterTypeDefCount++;
      sb.append(compareTypeParameterAndInsert(parameter, parameterType, name));
    }
  }

  public String buildInsertIntoParameterAndParameterValueSQL(Model model, List<ModelDTO> models) {
    StringBuilder sb = new StringBuilder();

    List<String> prime =
        Optional.ofNullable(model.getMetadata().getPrime()).orElse(Collections.emptyList());

    List<HyperParameter> parametersInPrime = new ArrayList<>();
    List<HyperParameter> parametersNotInPrime = new ArrayList<>();

    for (HyperParameter parameter : model.getHyperParameters()) {
      if (prime.contains(parameter.getName())) {
        parametersInPrime.add(parameter);
      } else {
        parametersNotInPrime.add(parameter);
      }
    }

    List<HyperParameter> orderedParameters = new ArrayList<>();
    orderedParameters.addAll(parametersInPrime);
    orderedParameters.addAll(parametersNotInPrime);

    sb.append(insertIntoParameterAndParameterValue(orderedParameters, model.getName(), models));

    return sb.toString();
  }
}
