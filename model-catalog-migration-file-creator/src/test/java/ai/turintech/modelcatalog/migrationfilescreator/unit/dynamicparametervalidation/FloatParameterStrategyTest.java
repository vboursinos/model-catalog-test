package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicparametervalidation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.migrationfilescreator.model.*;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.typeparameters.FloatParameterStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FloatParameterStrategyTest {
  private static String parameterName;
  private static String modelName;

  private static String parameterType;

  private static HyperParameter hyperParameter;

  private static ParameterDTO parameterDTO;
  private static ParameterTypeDistribution parameterTypeDistribution;

  private static ParameterTypeDefinitionDTO parameterTypeDefinitionDTO;

  FloatParameterStrategy floatParameterStrategy = new FloatParameterStrategy();

  private static final String EXPECTED_OUTPUT_INSERT_PATH =
      "src/test/resources/expectedoutput/unit_float_parameter_insert.txt";
  private static final String EXPECTED_OUTPUT_UPDATE_PATH =
      "src/test/resources/expectedoutput/unit_float_parameter_update.txt";
  private static final String EXPECTED_OUTPUT_DELETE_PATH =
      "src/test/resources/expectedoutput/unit_float_parameter_delete.txt";

  @BeforeAll
  public static void setUp() {
    parameterName = "parameterNameTest";
    modelName = "modelNameTest";
    parameterType = "parameterTypeTest";

    Interval interval = new Interval();
    interval.setLower(1.0);
    interval.setUpper(10.0);
    interval.setLeft(true);
    interval.setRight(true);
    FloatSet floatSet = new FloatSet();
    floatSet.setIntervals(List.of(interval));
    Domain domain = new Domain();
    domain.setFloatSet(floatSet);

    hyperParameter = new HyperParameter();
    hyperParameter.setName(parameterName);
    hyperParameter.setLabel("label");
    hyperParameter.setDescription("description");
    hyperParameter.setEnabled(true);
    hyperParameter.setFixedValue(true);
    hyperParameter.setOrdering(1);
    hyperParameter.setDomain(domain);
    hyperParameter.setDefaultValue(1.0);

    parameterTypeDistribution = new ParameterTypeDistribution();
    parameterTypeDistribution.setParameterType(parameterType);
    parameterTypeDistribution.setParameterDistribution("parameterDistributionTypeTest");
    parameterTypeDistribution.setParameterName(parameterName);
    parameterTypeDistribution.setFloatSet(floatSet);

    parameterDTO = new ParameterDTO();
    parameterDTO.setName(parameterName);
    parameterDTO.setLabel("label");
    parameterDTO.setDescription("description");
    parameterDTO.setEnabled(true);
    parameterDTO.setFixedValue(true);
    parameterDTO.setOrdering(1);

    FloatParameterRangeDTO floatParameterRangeDTO = new FloatParameterRangeDTO();
    floatParameterRangeDTO.setLower(1.0);
    floatParameterRangeDTO.setUpper(10.0);
    floatParameterRangeDTO.setIsLeftOpen(true);
    floatParameterRangeDTO.setIsRightOpen(true);

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("float");
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("parameterDistributionTypeTest");
    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setDefaultValue(1.0);
    floatParameterDTO.setFloatParameterRanges(Set.of(floatParameterRangeDTO));

    parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setFloatParameter(floatParameterDTO);
    parameterTypeDefinitionDTO.setOrdering(1);
  }

  @Test
  public void appendParameterSQLTest() {
    String query =
        floatParameterStrategy.appendParameterSQL(
            modelName, hyperParameter, parameterTypeDistribution);
    String expectedQueryPath = EXPECTED_OUTPUT_INSERT_PATH;
    validateContent(query, expectedQueryPath);
  }

  @Test
  public void appendUpdateTypeParameterSQLTest() {
    String query =
        floatParameterStrategy.appendUpdateTypeParameterSQL(
            modelName, hyperParameter, parameterTypeDistribution);
    String expectedQueryPath = EXPECTED_OUTPUT_UPDATE_PATH;
    validateContent(query, expectedQueryPath);
  }

  @Test
  public void appendTypeParameterAuditSQLTest() {
    String query =
        floatParameterStrategy.appendTypeParameterAuditSQL(
            modelName, parameterDTO, parameterTypeDefinitionDTO, 1);
    String expectedQuery =
        "INSERT INTO float_parameter_aud(id, rev, revtype, default_value, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = 'parameterNameTest' and model_id = (select id from model where name = 'modelNameTest') and parameter_type_id = (select id from parameter_type where name = 'float'))), (select max(rev) from revinfo), 1, 1.0,";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendDeleteTypeParameterSQLTest() {
    String query =
        floatParameterStrategy.appendDeleteTypeParameterSQL(
            parameterTypeDefinitionDTO, parameterTypeDistribution, modelName, parameterName);
    String expectedQuery = EXPECTED_OUTPUT_DELETE_PATH;
    validateContent(query, expectedQuery);
  }

  public void validateContent(String insertQuery, String expectedOutput) {
    try (BufferedReader br =
        new BufferedReader(new FileReader(expectedOutput, Charset.defaultCharset()))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
        Assertions.assertTrue(insertQuery.contains(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
