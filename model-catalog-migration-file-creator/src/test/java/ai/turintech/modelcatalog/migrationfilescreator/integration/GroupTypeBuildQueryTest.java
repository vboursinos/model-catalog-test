package ai.turintech.modelcatalog.migrationfilescreator.integration;

import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.GroupTypeCreator;
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
public class GroupTypeBuildQueryTest extends BaseBuildQueryTest {

  @Autowired private GroupTypeCreator groupTypeCreator;

  @Test
  public void testQueryBuilder() {
    groupTypeCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
  }

  private void validateContent() {
    try (BufferedReader br =
        new BufferedReader(new FileReader(FILE_NAME, Charset.defaultCharset()))) {
      String line;
      boolean foundInsertGroupQuery = false;
      boolean foundDeleteGroupQuery = false;

      while ((line = br.readLine()) != null) {
        System.out.println(line);
        if (line.contains("INSERT INTO model_group_type(name) VALUES ('test');")) {
          foundInsertGroupQuery = true;
        }
        if (line.contains("DELETE FROM model_group_type WHERE name='fast';")) {
          foundDeleteGroupQuery = true;
        }
      }
      Assertions.assertTrue(foundInsertGroupQuery);
      Assertions.assertTrue(foundDeleteGroupQuery);
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }
}
