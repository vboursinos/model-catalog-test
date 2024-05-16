package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.EnsembleFamilyCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class FamilyTypeBuildQueryTest {

  @Autowired private EnsembleFamilyCreator ensembleFamilyCreator;

  @Test
  public void testQueryBuilder() {
    String actualResult = ensembleFamilyCreator.createStaticTable();
    String expectedResult = "INSERT INTO model_family_type(name) VALUES ('family-test');";
    Assertions.assertTrue(actualResult.contains(expectedResult));
  }
}
