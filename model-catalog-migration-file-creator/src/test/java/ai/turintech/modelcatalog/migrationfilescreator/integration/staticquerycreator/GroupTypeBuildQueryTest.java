package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.GroupTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class GroupTypeBuildQueryTest {

  @Autowired private GroupTypeCreator groupTypeCreator;

  @Test
  public void testQueryBuilder() {
    String actualResult = groupTypeCreator.createStaticTable();
    String expectedInsertResult = "INSERT INTO model_group_type(name) VALUES ('test');";
    String expectedDeleteResult = "DELETE FROM model_group_type WHERE name='fast';";

    Assertions.assertTrue(actualResult.contains(expectedInsertResult));
    Assertions.assertTrue(actualResult.contains(expectedDeleteResult));
  }
}
