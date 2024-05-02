package integration;

import java.io.*;
import java.nio.charset.Charset;
import migration_files_creator.static_query_creator.GroupTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import unit.static_query_creator.BaseQueryValidationTest;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class GroupTypeBuildQueryTest extends BaseQueryValidationTest {

  @Autowired private GroupTypeCreator groupTypeCreator;

  private static final String FILE_NAME = "src/test/java/integration/migration_file.sql";

  @Test
  public void testQueryBuilder() {
    groupTypeCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
    file.delete();
  }

  private void validateContent() {
    try (BufferedReader br =
        new BufferedReader(new FileReader(FILE_NAME, Charset.defaultCharset()))) {
      String line;
      boolean foundGroupQuery = false;

      while ((line = br.readLine()) != null) {
        if (line.contains("INSERT INTO model_group_type(name) VALUES ('test');")) {
          foundGroupQuery = true;
          break;
        }
      }
      Assertions.assertTrue(foundGroupQuery);
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }
}
