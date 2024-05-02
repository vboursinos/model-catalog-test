package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.typeparameters;

import static ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.ParameterTablesBuilder.*;
import static ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.TableCreatorHelper.buildRevInfoInsertSQL;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.delete.DeleteTableBuilder;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.ParameterTablesBuilder;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import ai.turintech.modelcatalog.migrationfilescreator.model.Range;
import org.springframework.stereotype.Component;

@Component
class IntegerParameterStrategy extends TypeParameterUtils implements ParameterStrategy {
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
    StringBuilder sb = new StringBuilder();

    sb.append(
        appendIntegerParameterSQL(
            modelName, parameter.getName(), "integer", parameter.getDefaultValue()));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterIntegerAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDistribution.getParameterType(),
            0,
            getDefaultValue(parameter.getDefaultValue())));
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
        buildInsertTypeParameterIntegerAuditSQL(
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
    int lower = parameterTypeDistribution.getIntegerSet().getRanges().get(0).getStart();
    int upper = parameterTypeDistribution.getIntegerSet().getRanges().get(0).getStop();
    sb.append(
        appendIntegerParameterRangeSQL(parameter.getName(), parameterTypeDistribution, modelName));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterValueIntegerAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDistribution.getParameterType(),
            0,
            lower,
            upper));
    return sb.toString();
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
        buildInsertTypeParameterIntegerAuditSQL(
            parameter.getName(), modelName, parameterTypeDefinition, revType));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterValueIntegerAuditSQL(
            parameter.getName(), modelName, parameterTypeDefinition.getType().getName(), revType));

    return sb.toString();
  }

  @Override
  public String appendDeleteTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();

    if (shouldDeleteParameterType(parameterTypeDefinition, parameterTypeDistribution)) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          buildParameterTypeAuditAndDeletionSQL(
              parameterTypeDefinition, "integer_parameter", modelName, parameterName));
    }

    if (!rangesMatch(
        parameterTypeDefinition.getIntegerParameter().getIntegerParameterValues(),
        parameterTypeDistribution.getIntegerSet().getRanges())) {
      int lower = getLowerFromParameterDefinition(parameterTypeDefinition);
      int upper = getUpperFromParameterDefinition(parameterTypeDefinition);

      sb.append(buildRevInfoInsertSQL());
      sb.append(
          buildParameterValueIntegerAuditAndDeletionSQL(
              parameterTypeDefinition, 2, lower, upper, modelName, parameterName));
      sb.append(
          ParameterTablesBuilder.appendIntegerParameterRangeSQL(
              parameterName, parameterTypeDistribution, modelName));
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          buildParameterValueIntegerAuditAndDeletionSQL(
              parameterTypeDefinition, 0, lower, upper, modelName, parameterName));
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

    int lower = getLowerFromParameterDefinition(parameterTypeDefinition);
    int upper = getUpperFromParameterDefinition(parameterTypeDefinition);

    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildParameterValueIntegerAuditAndDeletionSQL(
            parameterTypeDefinition, 2, lower, upper, modelName, parameterName));
    sb.append(
        buildParameterTypeAuditAndDeletionSQL(
            parameterTypeDefinition, "integer_parameter", modelName, parameterName));
    return sb.toString();
  }

  private IntegerParameterValueDTO getIntegerParameterValue(
      ParameterTypeDefinitionDTO parameterTypeDefinition) {
    return parameterTypeDefinition.getIntegerParameter().getIntegerParameterValues().stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid parameter definition"));
  }

  private String buildInsertTypeParameterIntegerAuditSQL(
      String parameterName,
      String modelName,
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      int revType) {
    return buildInsertTypeParameterIntegerAuditSQL(
        parameterName,
        modelName,
        parameterTypeDefinition.getType().getName(),
        revType,
        getDefaultValue(parameterTypeDefinition.getIntegerParameter().getDefaultValue()));
  }

  private boolean shouldDeleteParameterType(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution) {
    return shouldDeleteParameterType(
        parameterTypeDefinition.getIntegerParameter().getDefaultValue(),
        parameterTypeDistribution.getDefaultValue());
  }

  private String buildParameterTypeAuditAndDeletionSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      String tableName,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterIntegerAuditSQL(
            parameterName, modelName, parameterTypeDefinition, 2));
    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeSql(
            parameterTypeDefinition, tableName, modelName, parameterName));
    return sb.toString();
  }

  private String buildParameterValueIntegerAuditAndDeletionSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      int revType,
      int lower,
      int upper,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();
    sb.append(
        buildInsertTypeParameterValueIntegerAuditSQL(
            parameterName,
            modelName,
            parameterTypeDefinition.getType().getName(),
            revType,
            lower,
            upper));
    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeValueSql(
            parameterTypeDefinition, "integer_parameter_value", modelName, parameterName));
    return sb.toString();
  }

  private int getLowerFromParameterDefinition(ParameterTypeDefinitionDTO parameterTypeDefinition) {
    return getIntegerParameterValue(parameterTypeDefinition).getLower();
  }

  private int getUpperFromParameterDefinition(ParameterTypeDefinitionDTO parameterTypeDefinition) {
    return getIntegerParameterValue(parameterTypeDefinition).getUpper();
  }

  public String buildUpdateTypeParameterSQL(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE integer_parameter SET default_value = ")
        .append(getDefaultValue(parameter.getDefaultValue()))
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

  public static String buildInsertTypeParameterIntegerAuditSQL(
      String parameter_name,
      String model_name,
      String parameter_type,
      int revType,
      Integer default_value) {
    if (revType == 2) {
      default_value = null;
    }
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO integer_parameter_aud(id, rev, revtype, default_value, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
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

  public static String buildInsertTypeParameterValueIntegerAuditSQL(
      String parameter_name, String model_name, String parameter_type, int revType) {
    return buildInsertTypeParameterValueIntegerAuditSQL(
        parameter_name, model_name, parameter_type, revType, null, null);
  }

  public static String buildInsertTypeParameterValueIntegerAuditSQL(
      String parameter_name,
      String model_name,
      String parameter_type,
      int revType,
      Integer lower,
      Integer upper) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    String subQuery =
        "(" + buildParameterTypeDefinitionQuery(model_name, parameter_name, parameter_type) + ")";
    if (revType == 2) {
      lower = null;
      upper = null;
      subQuery = null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO integer_parameter_value_aud(id, rev, revtype, lower, upper, parameter_type_definition_id,  created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("') and parameter_type_id = (select id from parameter_type where name = '")
        .append(parameter_type)
        .append("'))), (select max(rev) from revinfo), ")
        .append(revType)
        .append(", ")
        .append(lower)
        .append(", ")
        .append(upper)
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

    if (dbParameterTypeDefinition.getIntegerParameter() == null) {
      return true;
    }

    Integer value = dbParameterTypeDefinition.getIntegerParameter().getDefaultValue();
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
    return "integer";
  }

  private boolean rangesMatch(Set<IntegerParameterValueDTO> parameterValues, List<Range> ranges) {
    if (parameterValues.isEmpty() || ranges.isEmpty()) {
      return false;
    }

    Optional<IntegerParameterValueDTO> firstParamValue = parameterValues.stream().findFirst();
    Optional<Range> firstRange = ranges.stream().findFirst();

    IntegerParameterValueDTO firstParamValueValue =
        firstParamValue.orElseThrow(
            () -> new IllegalStateException("Parameter values set is empty"));
    Range firstRangeValue =
        firstRange.orElseThrow(() -> new IllegalStateException("Ranges list is empty"));

    return Objects.equals(firstParamValueValue.getLower(), firstRangeValue.getStart())
        && Objects.equals(firstParamValueValue.getUpper(), firstRangeValue.getStop());
  }

  private Integer getDefaultValue(Object defaultValue) {
    if (defaultValue == null) {
      return null;
    }
    if (defaultValue instanceof Integer) {
      return (Integer) defaultValue;
    }
    return null;
  }
}
