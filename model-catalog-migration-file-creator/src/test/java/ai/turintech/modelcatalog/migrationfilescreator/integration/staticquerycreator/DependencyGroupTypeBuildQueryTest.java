package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.DependencyGroupTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class DependencyGroupTypeBuildQueryTest {

  @Autowired private DependencyGroupTypeCreator dependencyGroupTypeCreator;

  @Test
  @Transactional
  public void testQueryBuilder() {
    String actualResult = dependencyGroupTypeCreator.createStaticTable();
    String expectedResult = "INSERT INTO dependency_group_type(name) VALUES ('base-test');";
    Assertions.assertTrue(actualResult.contains(expectedResult));
  }
}
