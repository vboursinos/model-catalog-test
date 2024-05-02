package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.delete;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;

public class DeleteTableBuilder {
  public static String buildDeleteSQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM model WHERE name='").append(model.getName()).append("';\n");
    return sb.toString();
  }

  public static String buildDeleteParameterSQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM parameter WHERE model_id=(select id from model where name='")
        .append(model.getName())
        .append("');\n");
    return sb.toString();
  }

  public static String buildDeleteParameterSQL(ParameterDTO parameter, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM parameter WHERE name='")
        .append(parameter.getName())
        .append("' and model_id=(select id from model where name='")
        .append(model.getName())
        .append("');\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeDefinitionSql(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append(
            "DELETE FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE model_id=(select id from model where name='")
        .append(model.getName())
        .append("'));\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeDefinitionSql(
      ParameterDTO parameter, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append(
            "DELETE FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE name='")
        .append(parameter.getName())
        .append("' and model_id=(select id from model where name='")
        .append(model.getName())
        .append("'));\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeDefinitionSql(
      ParameterTypeDefinitionDTO parameterTypeDefinition, String modelName, String parameterName) {
    StringBuilder sb = new StringBuilder();
    sb.append(
            "DELETE FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE name='")
        .append(parameterName)
        .append("' and model_id=(select id from model where name='")
        .append(modelName)
        .append("')) and parameter_type_id = (select id from parameter_type where name='")
        .append(parameterTypeDefinition.getType().getName())
        .append(
            "') and parameter_distribution_type_id = (select id from parameter_distribution_type where name='")
        .append(parameterTypeDefinition.getDistribution().getName())
        .append("');\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeSql(ModelDTO model, String parameterTypeTable) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM ")
        .append(parameterTypeTable)
        .append(
            " WHERE id IN (SELECT id FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE model_id=(select id from model where name='")
        .append(model.getName())
        .append("')));\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeSql(
      ParameterDTO parameter, String parameterTypeTable, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM ")
        .append(parameterTypeTable)
        .append(
            " WHERE id IN (SELECT id FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE name='")
        .append(parameter.getName())
        .append("' and model_id=(select id from model where name='")
        .append(model.getName())
        .append("')));\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeSql(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      String parameterTypeTable,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM ")
        .append(parameterTypeTable)
        .append(
            " WHERE id IN (SELECT id FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE name='")
        .append(parameterName)
        .append("' and model_id=(select id from model where name='")
        .append(modelName)
        .append("')) and parameter_type_id = (select id from parameter_type where name='")
        .append(parameterTypeDefinition.getType().getName())
        .append(
            "') and parameter_distribution_type_id = (select id from parameter_distribution_type where name='")
        .append(parameterTypeDefinition.getDistribution().getName())
        .append("'));\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeValueSql(ModelDTO model, String parameterTypeTable) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM ")
        .append(parameterTypeTable)
        .append(
            " WHERE parameter_type_definition_id IN (SELECT id FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE model_id=(select id from model where name='")
        .append(model.getName())
        .append("')));\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeValueSql(
      ParameterDTO parameter, String parameterTypeTable, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM ")
        .append(parameterTypeTable)
        .append(
            " WHERE parameter_type_definition_id IN (SELECT id FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE name='")
        .append(parameter.getName())
        .append("' and model_id=(select id from model where name='")
        .append(model.getName())
        .append("')));\n");
    return sb.toString();
  }

  public static String buildDeleteParameterTypeValueSql(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      String parameterTypeTable,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM ")
        .append(parameterTypeTable)
        .append(
            " WHERE parameter_type_definition_id IN (SELECT id FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE name='")
        .append(parameterName)
        .append("' and model_id=(select id from model where name='")
        .append(modelName)
        .append("')) and parameter_type_id = (select id from parameter_type where name='")
        .append(parameterTypeDefinition.getType().getName())
        .append(
            "') and parameter_distribution_type_id = (select id from parameter_distribution_type where name='")
        .append(parameterTypeDefinition.getDistribution().getName())
        .append("'));\n");
    return sb.toString();
  }
}
