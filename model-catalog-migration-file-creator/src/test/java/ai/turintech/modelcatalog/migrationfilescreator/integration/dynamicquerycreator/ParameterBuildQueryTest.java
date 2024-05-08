package ai.turintech.modelcatalog.migrationfilescreator.integration.dynamicquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.InsertParametersTables;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class ParameterBuildQueryTest {
  private static String parameterName;
  private static String modelName;

  private static String parameterType;

  private static HyperParameter hyperParameter;

  private static ParameterTypeDistribution parameterTypeDistribution;

  private static final String EXPECTED_OUTPUT_INSERT_PATH =
      "src/test/resources/expectedoutput/integration_parameter_compare_insert.txt";
  private static final String EXPECTED_OUTPUT_UPDATE_PATH =
      "src/test/resources/expectedoutput/integration_parameter_update_type_parameter.txt";

  @Autowired private InsertParametersTables insertParametersTables;

  @BeforeAll
  public static void setUp() {
    parameterName = "parameterNameTest";
    modelName = "modelNameTest";
    parameterType = "boolean";

    hyperParameter = new HyperParameter();
    hyperParameter.setName(parameterName);
    hyperParameter.setLabel("label");
    hyperParameter.setDescription("description");
    hyperParameter.setEnabled(true);
    hyperParameter.setFixedValue(true);
    hyperParameter.setOrdering(1);
    hyperParameter.setDefaultValue(false);

    parameterTypeDistribution = new ParameterTypeDistribution();
    parameterTypeDistribution.setParameterType(parameterType);
    parameterTypeDistribution.setParameterDistribution("parameterDistributionTypeTest");
    parameterTypeDistribution.setParameterName(parameterName);
  }

  @Test
  public void compareTypeParameterAndInsertTest() {
    String query =
        insertParametersTables.compareTypeParameterAndInsert(
            hyperParameter, parameterTypeDistribution, modelName);
    validateContent(query, EXPECTED_OUTPUT_INSERT_PATH);
  }

  @Test
  public void updateTypeParameterTest() {
    String query =
        insertParametersTables.updateTypeParameter(
            hyperParameter, parameterTypeDistribution, modelName);
    validateContent(query, EXPECTED_OUTPUT_UPDATE_PATH);
  }

  public void validateContent(String insertQuery, String expectedOutput) {
    try (BufferedReader br =
        new BufferedReader(new FileReader(expectedOutput, Charset.defaultCharset()))) {
      String line;
      while ((line = br.readLine()) != null) {
        Assertions.assertTrue(insertQuery.contains(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
