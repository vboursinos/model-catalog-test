package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.MlTaskCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class MlTaskBuildQueryTest {

  @Autowired private MlTaskCreator mlTaskCreator;

  @Test
  public void testQueryBuilder() {
    String actualResult = mlTaskCreator.createStaticTable();
    String expectedResult = "INSERT INTO ml_task_type(name) VALUES ('classification_2');";
    Assertions.assertTrue(actualResult.contains(expectedResult));
  }
}
