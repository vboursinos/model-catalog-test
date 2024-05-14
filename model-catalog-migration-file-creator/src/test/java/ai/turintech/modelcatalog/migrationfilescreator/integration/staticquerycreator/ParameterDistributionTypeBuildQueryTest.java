package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.ParameterDistributionTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class ParameterDistributionTypeBuildQueryTest {

  @Autowired private ParameterDistributionTypeCreator parameterDistributionTypeCreator;

  @Test
  public void testQueryBuilder() {
    String actualResult = parameterDistributionTypeCreator.createStaticTable();
    String expectedResult =
        "INSERT INTO parameter_distribution_type(name) VALUES ('uniform_test');";
    Assertions.assertTrue(actualResult.contains(expectedResult));
  }
}
