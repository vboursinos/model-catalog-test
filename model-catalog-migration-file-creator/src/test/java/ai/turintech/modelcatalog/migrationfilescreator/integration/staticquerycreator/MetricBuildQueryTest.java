package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.MetricsCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class MetricBuildQueryTest {

  @Autowired private MetricsCreator metricsCreator;

  @Test
  public void testQueryBuilder() {
    String expectedResult = "INSERT INTO metric(name) VALUES ('classification-log-loss-test');";
    String actualResult = metricsCreator.createStaticTable();
    Assertions.assertTrue(actualResult.contains(expectedResult));
  }
}
