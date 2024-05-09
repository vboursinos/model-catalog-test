package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicparametervalidation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ai.turintech.modelcatalog.migrationfilescreator.model.*;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.ParameterTablesBuilder;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParameterTableBuilderTest {

  private static String parameterName;
  private static String modelName;

  private static String parameterType;

  private static HyperParameter hyperParameter;

  private static ParameterTypeDistribution parameterTypeDistribution;

  @BeforeAll
  public static void setUp() {
    parameterName = "parameterNameTest";
    modelName = "modelNameTest";
    parameterType = "parameterTypeTest";

    hyperParameter = new HyperParameter();
    hyperParameter.setName(parameterName);
    hyperParameter.setLabel("label");
    hyperParameter.setDescription("description");
    hyperParameter.setEnabled(true);
    hyperParameter.setFixedValue(true);
    hyperParameter.setOrdering(1);

    parameterTypeDistribution = new ParameterTypeDistribution();
    parameterTypeDistribution.setParameterType(parameterType);
    parameterTypeDistribution.setParameterDistribution("parameterDistributionTypeTest");
    parameterTypeDistribution.setParameterName(parameterName);
  }

  @Test
  public void buildInsertParameterAuditSQLTest() {
    int revType = 0;
    String query =
        ParameterTablesBuilder.buildInsertParameterAuditSQL(modelName, parameterName, revType);
    String expectedQuery =
        "INSERT INTO parameter_aud(id, rev, revtype, name, label, description, enabled, fixed_value, ordering, model_id, created_at, updated_at) VALUES ((select id from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')), (select max(rev) from revinfo), 0, (select name from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')), (select label from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')), (select description from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')), (select enabled from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')), (select fixed_value from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')), (select ordering from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')), (select model_id from parameter where name = 'modelNameTest' and model_id = (select id from model where name = 'parameterNameTest')),";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void buildInsertParameterAuditSQLDeleteTest() {
    String query =
        ParameterTablesBuilder.buildInsertParameterAuditSQLDelete(parameterName, modelName);

    String expectedQuery =
        "INSERT INTO parameter_aud(id, rev, revtype, name, label, description, enabled, fixed_value, ordering, model_id, created_at, updated_at) VALUES ((select id from parameter where name = 'parameterNameTest' and model_id = (select id from model where name = 'modelNameTest')), (select max(rev) from revinfo), 2, null, null, null, null, null, null, null,";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void buildInsertParameterTypeDefinitionAuditSQLTest() {
    int revType = 0;
    String query =
        ParameterTablesBuilder.buildInsertParameterTypeDefinitionAuditSQL(
            parameterType, modelName, parameterType, revType);
    String expectedQuery =
        "INSERT INTO parameter_type_definition_aud(id, rev, revtype, parameter_id, parameter_type_id,parameter_distribution_type_id, ordering, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = 'parameterTypeTest' and model_id = (select id from model where name = 'modelNameTest')) and parameter_type_id = (select id from parameter_type where name = 'parameterTypeTest')), (select max(rev) from revinfo), 0, (select id from parameter where name = 'parameterTypeTest' and model_id = (select id from model where name = 'modelNameTest')), (select id from parameter_type where name = 'parameterTypeTest'), (select id from parameter_distribution_type where name = 'parameterTypeTest'), (select ordering from parameter_type_definition where parameter_id = (select id from parameter where name = 'parameterTypeTest' and model_id = (select id from model where name = 'modelNameTest'))),";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void buildInsertParameterTypeDefinitionAuditSQLDeleteTest() {
    String query =
        ParameterTablesBuilder.buildInsertParameterTypeDefinitionAuditSQLDelete(
            parameterName, modelName, parameterType);
    String expectedQuery =
        "INSERT INTO parameter_type_definition_aud(id, rev, revtype, parameter_id, parameter_type_id,parameter_distribution_type_id, ordering, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = 'parameterNameTest' and model_id = (select id from model where name = 'modelNameTest')) and parameter_type_id = (select id from parameter_type where name = 'parameterTypeTest')), (select max(rev) from revinfo), 2, null, null, null, null,";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void insertParameterSQLTest() {
    String description = "description";
    String query =
        ParameterTablesBuilder.insertParameterSQL(
            hyperParameter, description, hyperParameter.getOrdering(), modelName);
    String expectedQuery =
        "INSERT INTO parameter(name, label, description, enabled, fixed_value, ordering, model_id) VALUES ('parameterNameTest', 'label', 'description', true, true, 1, (select id from Model where name='modelNameTest'));\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void updateParameterSQLTest() {
    String query = ParameterTablesBuilder.updateParameterSQL(hyperParameter, modelName);
    String expectedQuery =
        "UPDATE parameter SET label = 'label', description = 'description', enabled = true, fixed_value = true, ordering = 1, model_id = (select id from Model where name='modelNameTest') WHERE name = 'parameterNameTest' and model_id = (select id from Model where name='modelNameTest');\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void buildInsertIntoParameterTypeDefinitionSQLTest() {
    String query =
        ParameterTablesBuilder.buildInsertIntoParameterTypeDefinitionSQL(
            hyperParameter, modelName, parameterTypeDistribution, 1);
    String expectedQuery =
        "INSERT INTO parameter_type_definition(parameter_id, parameter_type_id, parameter_distribution_type_id, ordering) VALUES ((select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest')), (select id from parameter_type where name='parameterTypeTest'), (select id from parameter_distribution_type where name='parameterDistributionTypeTest'), 1);\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendCategoricalParameterValueSQLTest() {
    String value = "value";
    String query =
        ParameterTablesBuilder.appendCategoricalParameterValueSQL(
            parameterName, parameterTypeDistribution, value, modelName);
    String expectedQuery =
        "INSERT INTO categorical_parameter_value(parameter_type_definition_id, value) VALUES ((select id from parameter_type_definition where parameter_id=(select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest') and parameter_type_id=(select id from parameter_type where name='parameterTypeTest'))), 'value');\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendFloatParameterSQLTest() {
    String query =
        ParameterTablesBuilder.appendFloatParameterSQL(
            modelName, parameterName, parameterTypeDistribution.getParameterType(), 0.1);
    String expectedQuery =
        "INSERT INTO float_parameter(id, default_value) VALUES ((select id from parameter_type_definition where parameter_id=(select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest') and parameter_type_id=(select id from parameter_type where name='parameterTypeTest'))), 0.100000);\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendFloatParameterRangeSQLTest() {
    Interval interval = new Interval();
    interval.setLeft(true);
    interval.setRight(true);
    interval.setLower(0.1);
    interval.setUpper(0.2);
    FloatSet floatSet = new FloatSet();
    floatSet.setIntervals(List.of(interval));
    parameterTypeDistribution.setFloatSet(floatSet);
    String query =
        ParameterTablesBuilder.appendFloatParameterRangeSQL(
            parameterName, parameterTypeDistribution, modelName);
    String expectedQuery =
        "INSERT INTO float_parameter_range(parameter_type_definition_id, is_left_open, is_right_open, lower, upper) VALUES ((select id from parameter_type_definition where parameter_id=(select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest') and parameter_type_id=(select id from parameter_type where name='parameterTypeTest'))), true, true, 0.100000, 0.200000);\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendIntegerParameterSQLTest() {
    int defaultValue = 1;
    String query =
        ParameterTablesBuilder.appendIntegerParameterSQL(
            modelName, parameterName, parameterTypeDistribution.getParameterType(), defaultValue);
    String expectedQuery =
        "INSERT INTO integer_parameter(id, default_value) VALUES ((select id from parameter_type_definition where parameter_id=(select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest') and parameter_type_id=(select id from parameter_type where name='parameterTypeTest'))), 1);\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendIntegerParameterRangeSQLTest() {
    Range range = new Range();
    range.setStart(1);
    range.setStop(2);
    IntegerSet integerSet = new IntegerSet();
    integerSet.setRanges(List.of(range));
    parameterTypeDistribution.setIntegerSet(integerSet);
    String query =
        ParameterTablesBuilder.appendIntegerParameterRangeSQL(
            parameterName, parameterTypeDistribution, modelName);
    String expectedQuery =
        "INSERT INTO integer_parameter_value(parameter_type_definition_id, lower, upper) VALUES ((select id from parameter_type_definition where parameter_id=(select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest') and parameter_type_id=(select id from parameter_type where name='parameterTypeTest'))), 1, 2);\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendBooleanParameterSQLTest() {
    boolean defaultValue = true;
    String query =
        ParameterTablesBuilder.appendBooleanParameterSQL(
            modelName, parameterName, parameterTypeDistribution.getParameterType(), defaultValue);
    String expectedQuery =
        "INSERT INTO boolean_parameter(id, default_value) VALUES ((select id from parameter_type_definition where parameter_id=(select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest') and parameter_type_id=(select id from parameter_type where name='parameterTypeTest'))), true);\n";
    assertTrue(query.contains(expectedQuery));
  }
}
