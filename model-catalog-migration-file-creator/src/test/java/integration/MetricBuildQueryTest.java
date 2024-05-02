package integration;

import java.io.*;
import java.nio.charset.Charset;
import migrationfilescreator.staticquerycreator.MetricsCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class MetricBuildQueryTest extends BaseBuildQueryTest {

  @Autowired private MetricsCreator metricsCreator;

  @Test
  public void testQueryBuilder() {
    metricsCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
  }

  private void validateContent() {
    try (BufferedReader br =
        new BufferedReader(new FileReader(FILE_NAME, Charset.defaultCharset()))) {
      String firstLine = br.readLine();
      Assertions.assertTrue(
          firstLine.contains("INSERT INTO metric(name) VALUES ('classification-log-loss-test');"));
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }
}
