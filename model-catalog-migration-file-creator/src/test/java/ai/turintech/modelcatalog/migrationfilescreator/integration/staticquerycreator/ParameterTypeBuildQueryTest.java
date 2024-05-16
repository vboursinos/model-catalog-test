package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.ParameterTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class ParameterTypeBuildQueryTest {

  @Autowired private ParameterTypeCreator parameterTypeCreator;

  @Test
  public void testQueryBuilder() {
    String actualResult = parameterTypeCreator.createStaticTable();
    String expectedInsertResult = "INSERT INTO parameter_type(name) VALUES ('test');";
    String expectedDeleteResult = "DELETE FROM parameter_type WHERE name='boolean';";
    Assertions.assertTrue(actualResult.contains(expectedInsertResult));
    Assertions.assertTrue(actualResult.contains(expectedDeleteResult));
  }
}
