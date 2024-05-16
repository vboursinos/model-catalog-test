package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.ModelTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class ModelTypeBuildQueryTest {

  @Autowired private ModelTypeCreator modelTypeCreator;

  @Test
  public void testQueryBuilder() {
    String actualResult = modelTypeCreator.createStaticTable();
    String expectedResult = "INSERT INTO model_type(name) VALUES ('Statistical Model test');";
    Assertions.assertTrue(actualResult.contains(expectedResult));
  }
}
