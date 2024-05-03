package ai.turintech.modelcatalog.migrationfilescreator.integration;

import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.MlTaskCreator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class MlTaskBuildQueryTest extends BaseBuildQueryTest {

  @Autowired private MlTaskCreator mlTaskCreator;

  @Test
  public void testQueryBuilder() {
    mlTaskCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
  }

  private void validateContent() {
    try (BufferedReader br =
        new BufferedReader(new FileReader(FILE_NAME, Charset.defaultCharset()))) {
      String firstLine = br.readLine();
      Assertions.assertTrue(
          firstLine.contains("INSERT INTO ml_task_type(name) VALUES ('classification_2');"));
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }
}
