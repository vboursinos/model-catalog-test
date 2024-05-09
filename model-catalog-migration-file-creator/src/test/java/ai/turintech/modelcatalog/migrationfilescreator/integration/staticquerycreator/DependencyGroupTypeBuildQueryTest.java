package ai.turintech.modelcatalog.migrationfilescreator.integration.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.DependencyGroupTypeCreator;
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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class DependencyGroupTypeBuildQueryTest extends BaseBuildQueryTest {

  @Autowired private DependencyGroupTypeCreator dependencyGroupTypeCreator;

  @Test
  @Transactional
  public void testQueryBuilder() {
    dependencyGroupTypeCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
  }

  private void validateContent() {
    try (BufferedReader br =
        new BufferedReader(new FileReader(FILE_NAME, Charset.defaultCharset()))) {
      String line;
      boolean foundInsertDependencyGroupTypeQuery = false;

      while ((line = br.readLine()) != null) {
        System.out.println(line);
        if (line.contains("INSERT INTO dependency_group_type(name) VALUES ('base-test');")) {
          foundInsertDependencyGroupTypeQuery = true;
        }
      }
      Assertions.assertTrue(foundInsertDependencyGroupTypeQuery);
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }
}
