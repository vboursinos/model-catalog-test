package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicparametervalidation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.typeparameters.BooleanParameterStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BooleanParameterStrategyTest {
  private static String parameterName;
  private static String modelName;

  private static String parameterType;

  private static HyperParameter hyperParameter;

  private static ParameterDTO parameterDTO;
  private static ParameterTypeDistribution parameterTypeDistribution;

  private static ParameterTypeDefinitionDTO parameterTypeDefinitionDTO;
  BooleanParameterStrategy booleanParameterStrategy = new BooleanParameterStrategy();

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

    parameterDTO = new ParameterDTO();
    parameterDTO.setName(parameterName);
    parameterDTO.setLabel("label");
    parameterDTO.setDescription("description");
    parameterDTO.setEnabled(true);
    parameterDTO.setFixedValue(true);
    parameterDTO.setOrdering(1);

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("boolean");
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("parameterDistributionTypeTest");
    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setDefaultValue(false);

    parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setBooleanParameter(booleanParameterDTO);
    parameterTypeDefinitionDTO.setOrdering(1);
  }

  @Test
  public void appendParameterSQLTest() {
    String query =
        booleanParameterStrategy.appendParameterSQL(
            modelName, hyperParameter, parameterTypeDistribution);
    String expectedQuery =
        "INSERT INTO boolean_parameter(id, default_value) VALUES ((select id from parameter_type_definition where parameter_id=(select id from parameter where name='parameterNameTest' and model_id=(select id from model where name='modelNameTest') and parameter_type_id=(select id from parameter_type where name='boolean'))), false);\n";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendUpdateTypeParameterSQLTest() {
    String query =
        booleanParameterStrategy.appendUpdateTypeParameterSQL(
            modelName, hyperParameter, parameterTypeDistribution);
    String expectedQuery =
        "UPDATE boolean_parameter SET default_value = null WHERE id = (SELECT id FROM parameter_type_definition WHERE parameter_id = (SELECT id FROM parameter WHERE name = 'parameterNameTest' AND model_id = (SELECT id FROM model WHERE name = 'modelNameTest') AND parameter_type_id = (SELECT id FROM parameter_type WHERE name = 'parameterTypeTest')));";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendTypeParameterAuditSQLTest() {
    String query =
        booleanParameterStrategy.appendTypeParameterAuditSQL(
            modelName, parameterDTO, parameterTypeDefinitionDTO, 1);
    String expectedQuery =
        "INSERT INTO boolean_parameter_aud(id, rev, revtype, default_value, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = 'parameterNameTest' and model_id = (select id from model where name = 'modelNameTest') and parameter_type_id = (select id from parameter_type where name = 'boolean'))), (select max(rev) from revinfo), 1, false,";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendDeleteTypeParameterSQLTest() {
    String query =
        booleanParameterStrategy.appendDeleteTypeParameterSQL(
            parameterTypeDefinitionDTO, parameterTypeDistribution, modelName, parameterName);
    String expectedQuery =
        "DELETE FROM boolean_parameter WHERE id IN (SELECT id FROM parameter_type_definition WHERE parameter_id IN (SELECT id FROM parameter WHERE name='parameterNameTest' and model_id=(select id from model where name='modelNameTest')) and parameter_type_id = (select id from parameter_type where name='boolean') and parameter_distribution_type_id = (select id from parameter_distribution_type where name='parameterDistributionTypeTest'));\n";
    assertTrue(query.contains(expectedQuery));
  }
}
