package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.ModelStructureCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class ModelStructureBuildQueryTest {

  @Autowired private ModelStructureCreator modelStructureCreator;

  @Test
  public void testQueryBuilder() {
    String actualResult = modelStructureCreator.createStaticTable();
    String expectedResult = "INSERT INTO model_structure_type(name) VALUES ('base_2');";
    Assertions.assertTrue(actualResult.contains(expectedResult));
  }
}
