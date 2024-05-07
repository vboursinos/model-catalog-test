package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicparametervalidation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.migrationfilescreator.model.*;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.typeparameters.IntegerParameterStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IntegerParameterStrategyTest {
  private static String parameterName;
  private static String modelName;

  private static String parameterType;

  private static HyperParameter hyperParameter;

  private static ParameterDTO parameterDTO;
  private static ParameterTypeDistribution parameterTypeDistribution;

  private static ParameterTypeDefinitionDTO parameterTypeDefinitionDTO;

  IntegerParameterStrategy integerParameterStrategy = new IntegerParameterStrategy();

  private static final String EXPECTED_OUTPUT_INSERT_PATH =
      "src/test/resources/expectedoutput/unit_integer_parameter_insert.txt";
  private static final String EXPECTED_OUTPUT_UPDATE_PATH =
      "src/test/resources/expectedoutput/unit_integer_parameter_update.txt";
  private static final String EXPECTED_OUTPUT_DELETE_PATH =
      "src/test/resources/expectedoutput/unit_integer_parameter_delete.txt";

  @BeforeAll
  public static void setUp() {
    parameterName = "parameterNameTest";
    modelName = "modelNameTest";
    parameterType = "parameterTypeTest";

    Range range = new Range();
    range.setStart(1);
    range.setStop(10);
    IntegerSet integerSet = new IntegerSet();
    integerSet.setRanges(List.of(range));
    Domain domain = new Domain();
    domain.setIntegerSet(integerSet);

    hyperParameter = new HyperParameter();
    hyperParameter.setName(parameterName);
    hyperParameter.setLabel("label");
    hyperParameter.setDescription("description");
    hyperParameter.setEnabled(true);
    hyperParameter.setFixedValue(true);
    hyperParameter.setOrdering(1);
    hyperParameter.setDomain(domain);

    parameterTypeDistribution = new ParameterTypeDistribution();
    parameterTypeDistribution.setParameterType(parameterType);
    parameterTypeDistribution.setParameterDistribution("parameterDistributionTypeTest");
    parameterTypeDistribution.setParameterName(parameterName);
    parameterTypeDistribution.setIntegerSet(integerSet);

    parameterDTO = new ParameterDTO();
    parameterDTO.setName(parameterName);
    parameterDTO.setLabel("label");
    parameterDTO.setDescription("description");
    parameterDTO.setEnabled(true);
    parameterDTO.setFixedValue(true);
    parameterDTO.setOrdering(1);

    IntegerParameterValueDTO integerParameterValueDTO = new IntegerParameterValueDTO();
    integerParameterValueDTO.setLower(1);
    integerParameterValueDTO.setUpper(10);

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("integer");
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("parameterDistributionTypeTest");
    IntegerParameterDTO integerParameterDTO = new IntegerParameterDTO();
    integerParameterDTO.setDefaultValue(1);
    integerParameterDTO.setIntegerParameterValues(Set.of(integerParameterValueDTO));

    parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setIntegerParameter(integerParameterDTO);
    parameterTypeDefinitionDTO.setOrdering(1);
  }

  @Test
  public void appendParameterSQLTest() {
    String query =
        integerParameterStrategy.appendParameterSQL(
            modelName, hyperParameter, parameterTypeDistribution);
    String expectedQueryPath = EXPECTED_OUTPUT_INSERT_PATH;
    validateContent(query, expectedQueryPath);
  }

  @Test
  public void appendUpdateTypeParameterSQLTest() {
    String query =
        integerParameterStrategy.appendUpdateTypeParameterSQL(
            modelName, hyperParameter, parameterTypeDistribution);
    String expectedQueryPath = EXPECTED_OUTPUT_UPDATE_PATH;
    validateContent(query, expectedQueryPath);
  }

  @Test
  public void appendTypeParameterAuditSQLTest() {
    String query =
        integerParameterStrategy.appendTypeParameterAuditSQL(
            modelName, parameterDTO, parameterTypeDefinitionDTO, 1);
    String expectedQuery =
        "INSERT INTO integer_parameter_aud(id, rev, revtype, default_value, created_at, updated_at) VALUES ((select id from parameter_type_definition where parameter_id = (select id from parameter where name = 'parameterNameTest' and model_id = (select id from model where name = 'modelNameTest') and parameter_type_id = (select id from parameter_type where name = 'integer'))), (select max(rev) from revinfo), 1, 1,";
    assertTrue(query.contains(expectedQuery));
  }

  @Test
  public void appendDeleteTypeParameterSQLTest() {
    String query =
        integerParameterStrategy.appendDeleteTypeParameterSQL(
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
