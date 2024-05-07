package ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.typeparameters;

import static ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.TableCreatorHelper.buildRevInfoInsertSQL;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.delete.DeleteTableBuilder;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.ParameterTablesBuilder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class CategoricalParameterStrategy extends TypeParameterUtils implements ParameterStrategy {
  @Override
  public String appendParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution) {
    StringBuilder sb = new StringBuilder();
    sb.append(appendTypeParameterSQL(modelName, parameter, parameterTypeDistribution));
    sb.append(appendTypeParameterValueSQL(modelName, parameter, parameterTypeDistribution));
    return sb.toString();
  }

  @Override
  public String appendTypeParameterSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution) {
    String defaultValue = getDefaultValue(parameter.getDefaultValue());

    return new StringBuilder()
        .append(
            ParameterTablesBuilder.appendCategoricalParameterSQL(
                modelName,
                parameter.getName(),
                "categorical",
                parameterTypeDistribution.getDefaultValue()))
        .append(buildRevInfoInsertSQL())
        .append(
            buildInsertTypeParameterCategoricalAuditSQL(
                parameter.getName(),
                modelName,
                parameterTypeDistribution.getParameterType(),
                0,
                defaultValue))
        .toString();
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
        buildInsertTypeParameterCategoricalAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDistribution.getParameterType(),
            1,
            getDefaultValue(parameter.getDefaultValue())));
    return sb.toString();
  }

  @Override
  public String appendTypeParameterValueSQL(
      String modelName,
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution) {
    StringBuilder sb = new StringBuilder();
    for (Object value : parameter.getDomain().getCategoricalSet().getCategories()) {
      sb.append(
          ParameterTablesBuilder.appendCategoricalParameterValueSQL(
              parameter.getName(), parameterTypeDistribution, value.toString(), modelName));
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          buildInsertTypeParameterValueCategoricalAuditSQL(
              parameter.getName(),
              modelName,
              parameterTypeDistribution.getParameterType(),
              0,
              value.toString()));
    }
    return sb.toString();
  }

  @Override
  public String appendTypeParameterAuditSQL(
      String modelName,
      ParameterDTO parameter,
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      int revType) {

    StringBuilder sb =
        new StringBuilder()
            .append(buildRevInfoInsertSQL())
            .append(
                buildInsertTypeParameterCategoricalAuditSQL(
                    parameter.getName(),
                    modelName,
                    parameterTypeDefinition.getType().getName(),
                    revType,
                    parameterTypeDefinition.getCategoricalParameter().getDefaultValue()));

    for (Object value :
        parameterTypeDefinition.getCategoricalParameter().getCategoricalParameterValues()) {
      sb.append(buildRevInfoInsertSQL())
          .append(
              buildInsertTypeParameterValueCategoricalAuditSQL(
                  parameter.getName(),
                  modelName,
                  parameterTypeDefinition.getType().getName(),
                  revType,
                  value.toString()));
    }

    return sb.toString();
  }

  @Override
  public String appendDeleteTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName) {

    StringBuilder sb = new StringBuilder();

    if (shouldDeleteParameterType(
        parameterTypeDefinition.getCategoricalParameter().getDefaultValue(),
        parameterTypeDistribution.getDefaultValue())) {

      sb.append(buildRevInfoInsertSQL())
          .append(
              buildInsertTypeParameterCategoricalAuditSQL(
                  parameterName,
                  modelName,
                  parameterTypeDefinition.getType().getName(),
                  2,
                  parameterTypeDefinition.getCategoricalParameter().getDefaultValue()))
          .append(
              DeleteTableBuilder.buildDeleteParameterTypeSql(
                  parameterTypeDefinition, "categorical_parameter", modelName, parameterName));
    }

    handleCategoricalParameterValues(
        parameterTypeDefinition, parameterTypeDistribution, sb, modelName, parameterName);

    return sb.toString();
  }

  private void handleCategoricalParameterValues(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      StringBuilder sb,
      String modelName,
      String parameterName) {
    Set<CategoricalParameterValueDTO> categoricalParameterValues =
        parameterTypeDefinition.getCategoricalParameter().getCategoricalParameterValues();
    List<String> categories = parameterTypeDistribution.getCategoricalSet().getCategories();

    boolean isPresent = allCategoriesPresent(categoricalParameterValues, categories);

    if (!isPresent) {
      for (CategoricalParameterValueDTO categoricalParameterValue : categoricalParameterValues) {
        appendAuditSQL(
            parameterTypeDefinition,
            2,
            sb,
            categoricalParameterValue.getValue(),
            modelName,
            parameterName);
      }

      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeValueSql(
              parameterTypeDefinition, "categorical_parameter_value", modelName, parameterName));

      for (Object category : categories) {
        appendNewCategoricalValue(
            parameterTypeDefinition,
            parameterTypeDistribution,
            sb,
            category.toString(),
            modelName,
            parameterName);
      }
    }
  }

  private void appendAuditSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      int revType,
      StringBuilder sb,
      String value,
      String modelName,
      String parameterName) {
    sb.append(buildRevInfoInsertSQL())
        .append(
            buildInsertTypeParameterValueCategoricalAuditSQL(
                parameterName,
                modelName,
                parameterTypeDefinition.getType().getName(),
                revType,
                value));
  }

  private void appendNewCategoricalValue(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      StringBuilder sb,
      String category,
      String modelName,
      String parameterName) {
    sb.append(
        ParameterTablesBuilder.appendCategoricalParameterValueSQL(
            parameterName, parameterTypeDistribution, category, modelName));

    appendAuditSQL(parameterTypeDefinition, 0, sb, category, modelName, parameterName);
  }

  @Override
  public String appendDeleteWithoutCheckTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();
    Set<CategoricalParameterValueDTO> categoricalParameterValues =
        parameterTypeDefinition.getCategoricalParameter().getCategoricalParameterValues();
    for (CategoricalParameterValueDTO categoricalParameterValue : categoricalParameterValues) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          buildInsertTypeParameterValueCategoricalAuditSQL(
              parameterName,
              modelName,
              parameterTypeDefinition.getType().getName(),
              2,
              categoricalParameterValue.getValue()));
    }
    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeValueSql(
            parameterTypeDefinition, "categorical_parameter_value", modelName, parameterName));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterCategoricalAuditSQL(
            parameterName,
            modelName,
            parameterTypeDefinition.getType().getName(),
            2,
            parameterTypeDefinition.getCategoricalParameter().getDefaultValue()));
    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeSql(
            parameterTypeDefinition, "categorical_parameter", modelName, parameterName));

    return sb.toString();
  }

  public String buildUpdateTypeParameterSQL(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE categorical_parameter SET default_value = '")
        .append(getDefaultValue(parameter.getDefaultValue()))
        .append(
            "' WHERE id = (SELECT id FROM parameter_type_definition WHERE parameter_id = (SELECT id FROM parameter WHERE name = '")
        .append(parameter.getName())
        .append("' AND model_id = (SELECT id FROM model WHERE name = '")
        .append(modelName)
        .append("') AND parameter_type_id = (SELECT id FROM parameter_type WHERE name = '")
        .append(parameterTypeDistribution.getParameterType())
        .append("')));\n");
    return sb.toString();
  }

  public static String buildInsertTypeParameterCategoricalAuditSQL(
      String parameter_name,
      String model_name,
      String parameter_type,
      int revType,
      String default_value) {
    if (revType == 2) {
      default_value = null;
    } else {
      default_value = "'" + default_value + "'";
    }
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO categorical_parameter_aud(id, rev, revtype, default_value, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
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

  public static String buildInsertTypeParameterValueCategoricalAuditSQL(
      String parameter_name, String model_name, String parameter_type, int revType, String value) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    value = "'" + value + "'";
    String subQuery =
        "("
            + ParameterTablesBuilder.buildParameterTypeDefinitionQuery(
                model_name, parameter_name, parameter_type)
            + ")";
    if (revType == 2) {
      value = null;
      subQuery = null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO categorical_parameter_value_aud(id, rev, revtype, value, parameter_type_definition_id,  created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("') and parameter_type_id = (select id from parameter_type where name = '")
        .append(parameter_type)
        .append("'))), (select max(rev) from revinfo), ")
        .append(revType)
        .append(", ")
        .append(value)
        .append(", ")
        .append(subQuery)
        .append(", '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }

  @Override
  public boolean compareTypeParameter(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      ParameterDTO dbParameter,
      ParameterTypeDefinitionDTO dbParameterTypeDefinition) {
    if (dbParameterTypeDefinition.getCategoricalParameter() == null) {
      return true;
    }
    String value = dbParameterTypeDefinition.getCategoricalParameter().getDefaultValue();
    if (value == null) {
      return true;
    }

    if (value.equals(parameter.getDefaultValue())) {
      return true;
    }
    return false;
  }

  @Override
  public String getParameterTypeName() {
    return "categorical";
  }

  private String getDefaultValue(Object defaultValue) {
    if (defaultValue != null && defaultValue instanceof String) {
      return (String) defaultValue;
    }
    return null;
  }
}
