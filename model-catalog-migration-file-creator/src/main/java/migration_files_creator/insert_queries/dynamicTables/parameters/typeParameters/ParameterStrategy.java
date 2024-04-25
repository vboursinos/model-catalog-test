package migration_files_creator.insert_queries.dynamicTables.parameters.typeParameters;

import database.dto.ParameterDTO;
import database.dto.ParameterTypeDefinitionDTO;
import migration_files_creator.model.HyperParameter;
import migration_files_creator.model.ParameterTypeDistribution;

public interface ParameterStrategy {
  String appendParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution);

  String appendTypeParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution);

  String appendUpdateTypeParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution);

  String appendTypeParameterValueSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution);

  String appendTypeParameterAuditSQL(
      String modelName,
      ParameterDTO parameter,
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      int revType);

  String appendDeleteTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName);

  String appendDeleteWithoutCheckTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName);

  boolean compareTypeParameter(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      ParameterDTO dbParameter,
      ParameterTypeDefinitionDTO dbParameterTypeDefinition);

  String getParameterTypeName();
}
