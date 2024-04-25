package migration_files_creator.insert_queries.dynamicTables.parameters;

import java.text.SimpleDateFormat;
import migration_files_creator.model.HyperParameter;
import migration_files_creator.model.ParameterTypeDistribution;

public class ParameterTablesBuilder {
  public static String buildInsertParameterAuditSQL(
      String parameter_name, String model_name, int revType) {
    if (revType == 2) {
      return buildInsertParameterAuditSQLDelete(parameter_name, model_name);
    }
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO parameter_aud(id, rev, revtype, name, label, description, enabled, fixed_value, ordering, model_id, created_at, updated_at) VALUES ((select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select max(rev) from revinfo), ")
        .append(revType)
        .append(", (select name from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select label from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select description from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select enabled from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select fixed_value from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select ordering from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select model_id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }

  public static String buildInsertParameterAuditSQLDelete(
      String parameter_name, String model_name) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO parameter_aud(id, rev, revtype, name, label, description, enabled, fixed_value, ordering, model_id, created_at, updated_at) VALUES ((select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select max(rev) from revinfo), ")
        .append(2)
        .append(", null, null, null, null, null, null, null, '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }

  public static String buildInsertParameterTypeDefinitionAuditSQL(
      String parameter_name, String model_name, String parameter_type, int revType) {
    if (revType == 2) {
      return buildInsertParameterTypeDefinitionAuditSQLDelete(
          parameter_name, model_name, parameter_type);
    }
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO parameter_type_definition_aud(id, rev, revtype, parameter_id, parameter_type_id,parameter_distribution_type_id, ordering, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')) and parameter_type_id = (select id from parameter_type where name = '")
        .append(parameter_type)
        .append("')), (select max(rev) from revinfo), ")
        .append(revType)
        .append(", (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')), (select id from parameter_type where name = '")
        .append(parameter_type)
        .append("'), (select id from parameter_distribution_type where name = '")
        .append(parameter_type)
        .append(
            "'), (select ordering from parameter_type_definition where parameter_id = (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("'))), '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }

  public static String buildInsertParameterTypeDefinitionAuditSQLDelete(
      String parameter_name, String model_name, String parameter_type) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO parameter_type_definition_aud(id, rev, revtype, parameter_id, parameter_type_id,parameter_distribution_type_id, ordering, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = '")
        .append(parameter_name)
        .append("' and model_id = (select id from model where name = '")
        .append(model_name)
        .append("')) and parameter_type_id = (select id from parameter_type where name = '")
        .append(parameter_type)
        .append("')), (select max(rev) from revinfo), ")
        .append(2)
        .append(", null, null, null, null, '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }

  public static String insertParameterSQL(
      HyperParameter parameter, String description, int count, String name) {
    return String.format(
        "INSERT INTO parameter(name, label, description, enabled, fixed_value, ordering, model_id) VALUES ('%s', '%s', '%s', %s, %s, %d, (select id from Model where name='%s'));\n",
        parameter.getName(),
        parameter.getLabel(),
        description,
        parameter.getEnabled(),
        parameter.isFixedValue(),
        count,
        name);
  }

  public static String updateParameterSQL(HyperParameter parameter, String name) {
    return "UPDATE parameter SET label = '"
        + parameter.getLabel()
        + "', description = '"
        + parameter.getDescription()
        + "', enabled = "
        + parameter.getEnabled()
        + ", fixed_value = "
        + parameter.isFixedValue()
        + ", ordering = "
        + parameter.getOrdering()
        + ", model_id = (select id from Model where name='"
        + name
        + "') WHERE name = '"
        + parameter.getName()
        + "' and model_id = (select id from Model where name='"
        + name
        + "');\n";
  }

  public static String buildInsertIntoParameterTypeDefinitionSQL(
      HyperParameter parameter, String name, ParameterTypeDistribution parameterType, int count) {
    StringBuilder sb = new StringBuilder();
    return sb.append(
            String.format(
                "INSERT INTO parameter_type_definition(parameter_id, parameter_type_id, "
                    + "parameter_distribution_type_id, ordering) VALUES ((select id from parameter where name='%s' "
                    + "and model_id=(select id from model where name='%s')), (select id from parameter_type where name='%s'), "
                    + "(select id from parameter_distribution_type where name='%s'), %d);\n",
                parameter.getName(),
                name,
                parameterType.getParameterType(),
                parameterType.getParameterDistribution(),
                count++))
        .toString();
  }

  public static String buildParameterTypeDefinitionQuery(
      String modelName, String parameterName, String parameterType) {
    String subQuery =
        String.format(
            "(select id from parameter_type_definition where parameter_id=(select id from parameter where name='%s' "
                + "and model_id=(select id from model where name='%s') and parameter_type_id=(select id from parameter_type where name='%s')))",
            parameterName, modelName, parameterType);
    return subQuery;
  }

  public static String appendCategoricalParameterSQL(
      String modelName, String parameterName, String parameterType, Object defaultValue) {
    String subQuery = buildParameterTypeDefinitionQuery(modelName, parameterName, parameterType);
    StringBuilder sb = new StringBuilder();
    String strDefaultValue = null;
    if (defaultValue != null) {
      if (defaultValue instanceof String) {
        strDefaultValue = (String) defaultValue;
      }
    }
    String insertSQL =
        strDefaultValue != null
            ? String.format(
                "INSERT INTO categorical_parameter(id, default_value) VALUES (%s, '%s');\n",
                subQuery, strDefaultValue)
            : String.format(
                "INSERT INTO categorical_parameter(id, default_value) VALUES (%s, %s);\n",
                subQuery, strDefaultValue);
    sb.append(insertSQL);
    return insertSQL;
  }

  public static String appendCategoricalParameterValueSQL(
      String parameter_name,
      ParameterTypeDistribution parameterTypeDistribution,
      String value,
      String modelName) {
    String subQuery =
        buildParameterTypeDefinitionQuery(
            modelName, parameter_name, parameterTypeDistribution.getParameterType());
    StringBuilder sb = new StringBuilder();
    String insertSQL =
        String.format(
            "INSERT INTO categorical_parameter_value(parameter_type_definition_id, value) VALUES (%s, '%s');\n",
            subQuery, value);
    sb.append(insertSQL);
    return insertSQL;
  }

  public static String appendFloatParameterSQL(
      String modelName, String parameterName, String parameterType, Object defaultValue) {
    String subQuery = buildParameterTypeDefinitionQuery(modelName, parameterName, parameterType);
    StringBuilder sb = new StringBuilder();
    Float floatDefaultValue = null;
    if (defaultValue != null) {
      if (defaultValue instanceof Float) {
        floatDefaultValue = (Float) defaultValue;
      } else if (defaultValue instanceof Double) {
        floatDefaultValue = ((Double) defaultValue).floatValue();
      }
    }
    String insertSQL =
        String.format(
            "INSERT INTO float_parameter(id, default_value) VALUES (%s, %f);\n",
            subQuery, floatDefaultValue);
    sb.append(insertSQL);
    return insertSQL;
  }

  public static String appendFloatParameterRangeSQL(
      String parameter_name,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {
    String subQuery =
        buildParameterTypeDefinitionQuery(
            modelName, parameter_name, parameterTypeDistribution.getParameterType());
    String insertSQL =
        String.format(
            "INSERT INTO float_parameter_range(parameter_type_definition_id, is_left_open, is_right_open, lower, upper) VALUES (%s, %s, %s, %f, %f);\n",
            subQuery,
            parameterTypeDistribution.getFloatSet().getIntervals().get(0).getLeft(),
            parameterTypeDistribution.getFloatSet().getIntervals().get(0).getRight(),
            parameterTypeDistribution.getFloatSet().getIntervals().get(0).getLower(),
            parameterTypeDistribution.getFloatSet().getIntervals().get(0).getUpper());
    return insertSQL;
  }

  public static String appendIntegerParameterSQL(
      String modelName, String parameterName, String parameterType, Object defaultValue) {
    String subQuery = buildParameterTypeDefinitionQuery(modelName, parameterName, parameterType);

    StringBuilder sb = new StringBuilder();
    Integer intDefaultValue = null;
    if (defaultValue != null) {
      if (defaultValue instanceof Integer) {
        intDefaultValue = (Integer) defaultValue;
      }
    }
    String insertSQL =
        String.format(
            "INSERT INTO integer_parameter(id, default_value) VALUES (%s, %d);\n",
            subQuery, intDefaultValue);
    sb.append(insertSQL);
    return insertSQL;
  }

  public static String appendIntegerParameterRangeSQL(
      String parameter_name,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName) {
    String subQuery =
        buildParameterTypeDefinitionQuery(
            modelName, parameter_name, parameterTypeDistribution.getParameterType());
    StringBuilder sb = new StringBuilder();
    String insertSQL =
        String.format(
            "INSERT INTO integer_parameter_value(parameter_type_definition_id, lower, upper) VALUES (%s, %d, %d);\n",
            subQuery,
            parameterTypeDistribution.getIntegerSet().getRanges().get(0).getStart(),
            parameterTypeDistribution.getIntegerSet().getRanges().get(0).getStop());
    sb.append(insertSQL);
    return insertSQL;
  }

  public static String appendBooleanParameterSQL(
      String modelName, String parameterName, String parameterType, Object defaultValue) {
    String subQuery = buildParameterTypeDefinitionQuery(modelName, parameterName, parameterType);
    StringBuilder sb = new StringBuilder();
    Boolean boolDefaultValue = null;
    if (defaultValue instanceof Boolean) {
      boolDefaultValue = (Boolean) defaultValue;
    }
    String insertSQL =
        String.format(
            "INSERT INTO boolean_parameter(id, default_value) VALUES (%s, %b);\n",
            subQuery, boolDefaultValue);
    sb.append(insertSQL);
    return insertSQL;
  }
}
