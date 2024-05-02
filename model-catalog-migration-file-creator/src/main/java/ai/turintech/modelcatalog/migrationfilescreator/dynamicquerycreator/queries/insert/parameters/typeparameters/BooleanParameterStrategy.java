package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.typeparameters;

import static ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.ParameterTablesBuilder.appendBooleanParameterSQL;
import static ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.TableCreatorHelper.buildRevInfoInsertSQL;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.delete.DeleteTableBuilder;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

@Component
public class BooleanParameterStrategy implements ParameterStrategy {
  @Override
  public String appendParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution) {
    return appendTypeParameterSQL(modelName, parameter, parameterTypeDistribution);
  }

  @Override
  public String appendTypeParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution) {
    StringBuilder sb = new StringBuilder();
    sb.append(
        appendBooleanParameterSQL(
            modelName, parameter.getName(), "boolean", parameter.getDefaultValue()));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterBooleanAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDistribution.getParameterType(),
            0,
            Boolean.TRUE.equals(getDefaultValue(parameter))));
    return sb.toString();
  }

  @Override
  public String appendUpdateTypeParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildUpdateTypeParameterSQL(parameter, parameterTypeDistribution, modelName));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterBooleanAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDistribution.getParameterType(),
            1,
            getDefaultValue(parameter)));
    return sb.toString();
  }

  @Override
  public String appendTypeParameterValueSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution) {
    return "";
  }

  @Override
  public String appendTypeParameterAuditSQL(
      String modelName,
      ParameterDTO parameter,
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      int revType) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterBooleanAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDefinition.getType().getName(),
            revType,
            parameterTypeDefinition.getBooleanParameter().getDefaultValue()));
    return sb.toString();
  }

  public String buildUpdateTypeParameterSQL(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE boolean_parameter SET default_value = ")
        .append(parameter.getDefaultValue())
        .append(
            " WHERE id = (SELECT id FROM parameter_type_definition WHERE parameter_id = (SELECT id FROM parameter WHERE name = '")
        .append(parameter.getName())
        .append("' AND model_id = (SELECT id FROM model WHERE name = '")
        .append(modelName)
        .append("') AND parameter_type_id = (SELECT id FROM parameter_type WHERE name = '")
        .append(parameterTypeDistribution.getParameterType())
        .append("')));\n");
    return sb.toString();
  }

  public static String buildInsertTypeParameterBooleanAuditSQL(
      String parameter_name,
      String model_name,
      String parameter_type,
      int revType,
      Boolean default_value) {
    if (revType == 2) {
      default_value = null;
    }
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO boolean_parameter_aud(id, rev, revtype, default_value, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("') and parameter_type_id = (select id from parameter_type where name = '")
        .append(parameter_type)
        .append("'))), (select max(rev) from revinfo), ")
        .append(revType)
        .append(", ")
        .append(default_value)
        .append(", '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }

  @Override
  public String appendDeleteTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();
    if (!parameterTypeDefinition
        .getBooleanParameter()
        .getDefaultValue()
        .equals(parameterTypeDistribution.getDefaultValue())) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          buildInsertTypeParameterBooleanAuditSQL(
              parameterName,
              modelName,
              parameterTypeDefinition.getType().getName(),
              2,
              parameterTypeDefinition.getBooleanParameter().getDefaultValue()));
      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeSql(
              parameterTypeDefinition, "boolean_parameter", modelName, parameterName));
    }
    return sb.toString();
  }

  @Override
  public String appendDeleteWithoutCheckTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterBooleanAuditSQL(
            parameterName,
            modelName,
            parameterTypeDefinition.getType().getName(),
            2,
            parameterTypeDefinition.getBooleanParameter().getDefaultValue()));
    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeSql(
            parameterTypeDefinition, "boolean_parameter", modelName, parameterName));

    return sb.toString();
  }

  @Override
  public boolean compareTypeParameter(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      ParameterDTO dbParameter,
      ParameterTypeDefinitionDTO dbParameterTypeDefinition) {
    if (dbParameterTypeDefinition.getBooleanParameter() == null) {
      return true;
    }
    if (dbParameterTypeDefinition
        .getBooleanParameter()
        .getDefaultValue()
        .equals(parameter.getDefaultValue())) {
      return true;
    }
    return false;
  }

  @Override
  public String getParameterTypeName() {
    return "boolean";
  }

  private Boolean getDefaultValue(HyperParameter parameter) {
    Object defaultValue = parameter.getDefaultValue();
    Boolean boolDefaultValue = null;
    if (defaultValue instanceof Boolean) {
      boolDefaultValue = (Boolean) defaultValue;
    }
    return boolDefaultValue;
  }
}
