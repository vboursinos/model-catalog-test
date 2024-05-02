package integration;

import java.io.*;
import java.nio.charset.Charset;
import migration_files_creator.static_query_creator.EnsembleFamilyCreator;
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
public class FamilyTypeBuildQueryTest extends BaseQueryValidationTest {

  @Autowired private EnsembleFamilyCreator ensembleFamilyCreator;

  private static final String FILE_NAME = "src/test/java/integration/migration_file.sql";

  @Test
  public void testQueryBuilder() {
    ensembleFamilyCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
    file.delete();
  }

  private void validateContent() {
    try (BufferedReader br =
        new BufferedReader(new FileReader(FILE_NAME, Charset.defaultCharset()))) {
      String line;
      boolean foundFamily = false;
      boolean foundEnsemble = false;

      while ((line = br.readLine()) != null) {
        if (line.contains("INSERT INTO model_family_type(name) VALUES ('family-test');")) {
          foundFamily = true;
        }
        if (line.contains("INSERT INTO model_ensemble_type(name) VALUES ('ensemble-test');")) {
          foundEnsemble = true;
        }
      }
      Assertions.assertTrue(foundFamily);
      Assertions.assertTrue(foundEnsemble);
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }
}
