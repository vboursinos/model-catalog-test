package ai.turintech.modelcatalog.migrationfilescreator.integration;

import ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.ParameterTypeCreator;
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
public class ParameterTypeBuildQueryTest extends BaseBuildQueryTest {

  @Autowired private ParameterTypeCreator parameterTypeCreator;

  @Test
  public void testQueryBuilder() {
    parameterTypeCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
  }

  private void validateContent() {
    try (BufferedReader br =
        new BufferedReader(new FileReader(FILE_NAME, Charset.defaultCharset()))) {
      String line;
      boolean foundInsertParameterTypeQuery = false;
      boolean foundDeleteParameterQuery = false;

      while ((line = br.readLine()) != null) {
        System.out.println(line);
        if (line.contains("INSERT INTO parameter_type(name) VALUES ('test');")) {
          foundInsertParameterTypeQuery = true;
        }
        if (line.contains("DELETE FROM parameter_type WHERE name='boolean';")) {
          foundDeleteParameterQuery = true;
        }
      }
      Assertions.assertTrue(foundInsertParameterTypeQuery);
      Assertions.assertTrue(foundDeleteParameterQuery);
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }
}
