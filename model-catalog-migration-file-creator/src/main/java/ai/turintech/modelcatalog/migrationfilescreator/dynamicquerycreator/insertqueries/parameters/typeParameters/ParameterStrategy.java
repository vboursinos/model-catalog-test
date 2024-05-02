package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.parameters.typeParameters;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;

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
