package ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.typeparameters;

import static ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.TableCreatorHelper.buildRevInfoInsertSQL;
import static ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.ParameterTablesBuilder.*;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.Interval;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.delete.DeleteTableBuilder;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.ParameterTablesBuilder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
class FloatParameterStrategy extends TypeParameterUtils implements ParameterStrategy {
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
        appendFloatParameterSQL(
            modelName, parameter.getName(), "float", parameter.getDefaultValue()));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterFloatAuditSQL(
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
        buildInsertTypeParameterFloatAuditSQL(
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
    boolean isLeftOpen = parameterTypeDistribution.getFloatSet().getIntervals().get(0).getLeft();
    boolean isRightOpen = parameterTypeDistribution.getFloatSet().getIntervals().get(0).getRight();
    double lower = parameterTypeDistribution.getFloatSet().getIntervals().get(0).getLower();
    double upper = parameterTypeDistribution.getFloatSet().getIntervals().get(0).getUpper();

    StringBuilder sb = new StringBuilder();
    sb.append(
        appendFloatParameterRangeSQL(parameter.getName(), parameterTypeDistribution, modelName));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterValueFloatAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDistribution.getParameterType(),
            0,
            isLeftOpen,
            isRightOpen,
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
        buildInsertTypeParameterFloatAuditSQL(
            parameter.getName(),
            modelName,
            parameterTypeDefinition.getType().getName(),
            revType,
            getDefaultValue(parameterTypeDefinition.getFloatParameter().getDefaultValue())));
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterValueFloatAuditSQL(
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
    FloatParameterRangeDTO firstFloatParameterRange =
        obtainFloatParameterRange(parameterTypeDefinition);

    if (shouldDeleteParameterType(
        parameterTypeDefinition.getFloatParameter().getDefaultValue(),
        parameterTypeDistribution.getDefaultValue())) {

      handleDefaultValueAudit(parameterTypeDefinition, sb, modelName, parameterName);
    }

    if (!floatRangesMatch(
        parameterTypeDefinition.getFloatParameter().getFloatParameterRanges(),
        parameterTypeDistribution.getFloatSet().getIntervals())) {

      handleFloatParameterRangeAudit(
          parameterTypeDefinition,
          parameterTypeDistribution,
          sb,
          firstFloatParameterRange,
          modelName,
          parameterName);
    }

    return sb.toString();
  }

  private void handleFloatParameterRangeAudit(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      StringBuilder sb,
      FloatParameterRangeDTO firstFloatParameterRange,
      String modelName,
      String parameterName) {
    appendFloatParameterRangeAudit(
        parameterTypeDefinition, 2, sb, firstFloatParameterRange, modelName, parameterName);

    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeValueSql(
            parameterTypeDefinition, "float_parameter_range", modelName, parameterName));

    sb.append(
        ParameterTablesBuilder.appendFloatParameterRangeSQL(
            parameterName, parameterTypeDistribution, modelName));

    appendFloatParameterRangeAudit(
        parameterTypeDefinition, 0, sb, firstFloatParameterRange, modelName, parameterName);
  }

  private void handleDefaultValueAudit(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      StringBuilder sb,
      String modelName,
      String parameterName) {
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterFloatAuditSQL(
            parameterName,
            modelName,
            parameterTypeDefinition.getType().getName(),
            2,
            getDefaultValue(parameterTypeDefinition.getFloatParameter().getDefaultValue())));
    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeSql(
            parameterTypeDefinition, "float_parameter", modelName, parameterName));
  }

  private FloatParameterRangeDTO obtainFloatParameterRange(
      ParameterTypeDefinitionDTO parameterTypeDefinition) {
    return parameterTypeDefinition.getFloatParameter().getFloatParameterRanges().stream()
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No float parameter range found"));
  }

  private void appendFloatParameterRangeAudit(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      int revType,
      StringBuilder sb,
      FloatParameterRangeDTO floatParameterRange,
      String modelName,
      String parameterName) {
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        buildInsertTypeParameterValueFloatAuditSQL(
            parameterName,
            modelName,
            parameterTypeDefinition.getType().getName(),
            revType,
            floatParameterRange.getIsLeftOpen(),
            floatParameterRange.getIsRightOpen(),
            floatParameterRange.getLower(),
            floatParameterRange.getUpper()));
  }

  @Override
  public String appendDeleteWithoutCheckTypeParameterSQL(
      ParameterTypeDefinitionDTO parameterTypeDefinition,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName,
      String parameterName) {

    StringBuilder sb = new StringBuilder();
    FloatParameterRangeDTO firstFloatParameterRange =
        obtainFloatParameterRange(parameterTypeDefinition);

    appendFloatParameterRangeAudit(
        parameterTypeDefinition, 2, sb, firstFloatParameterRange, modelName, parameterName);

    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeValueSql(
            parameterTypeDefinition, "float_parameter_range", modelName, parameterName));

    handleDefaultValueAudit(parameterTypeDefinition, sb, modelName, parameterName);

    return sb.toString();
  }

  public String buildUpdateTypeParameterSQL(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE float_parameter SET default_value = ")
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

  public static String buildInsertTypeParameterFloatAuditSQL(
      String parameter_name,
      String model_name,
      String parameter_type,
      int revType,
      Float default_value) {
    if (revType == 2) {
      default_value = null;
    }
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO float_parameter_aud(id, rev, revtype, default_value, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
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

  public static String buildInsertTypeParameterValueFloatAuditSQL(
      String parameter_name, String model_name, String parameter_type, int revType) {
    return FloatParameterStrategy.buildInsertTypeParameterValueFloatAuditSQL(
        parameter_name, model_name, parameter_type, revType, null, null, null, null);
  }

  public static String buildInsertTypeParameterValueFloatAuditSQL(
      String parameter_name,
      String model_name,
      String parameter_type,
      int revType,
      Boolean isLeftOpen,
      Boolean isRightOpen,
      Double lower,
      Double upper) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    String subQuery =
        "(" + buildParameterTypeDefinitionQuery(model_name, parameter_name, parameter_type) + ")";
    if (revType == 2) {
      subQuery = null;
      isLeftOpen = null;
      isRightOpen = null;
      lower = null;
      upper = null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO float_parameter_range_aud(id, rev, revtype, is_left_open, is_right_open, lower, upper, parameter_type_definition_id,  created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("') and parameter_type_id = (select id from parameter_type where name = '")
        .append(parameter_type)
        .append("'))), (select max(rev) from revinfo), ")
        .append(revType)
        .append(", ")
        .append(isLeftOpen)
        .append(", ")
        .append(isRightOpen)
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
    if (dbParameterTypeDefinition.getFloatParameter() == null) {
      return true;
    }

    Double value = dbParameterTypeDefinition.getFloatParameter().getDefaultValue();
    if (value == null) {
      return true;
    }
    Double defaultValue = (Double) parameter.getDefaultValue();

    if (value.compareTo(defaultValue) == 0) {
      return true;
    }
    return false;
  }

  @Override
  public String getParameterTypeName() {
    return "float";
  }

  private boolean floatRangesMatch(
      Set<FloatParameterRangeDTO> parameterRanges, List<Interval> intervals) {
    if (parameterRanges.isEmpty() || intervals.isEmpty()) {
      return false;
    }

    FloatParameterRangeDTO firstParamRange =
        parameterRanges.stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No float parameter range found"));
    Interval firstInterval =
        intervals.stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Intervals list is empty"));

    double tolerance = 0.0001;
    return Math.abs(firstParamRange.getLower() - firstInterval.getLower()) <= tolerance
        && Math.abs(firstParamRange.getUpper() - firstInterval.getUpper()) <= tolerance
        && Objects.equals(firstParamRange.getIsLeftOpen(), firstInterval.getLeft())
        && Objects.equals(firstParamRange.getIsRightOpen(), firstInterval.getRight());
  }

  private Float getDefaultValue(Object defaultValue) {
    if (defaultValue == null) {
      return null;
    }
    if (defaultValue instanceof Float) {
      return (Float) defaultValue;
    } else if (defaultValue instanceof Double) {
      return ((Double) defaultValue).floatValue();
    }
    return null;
  }
}
