package integration;

import migration_files_creator.static_query_creator.ModelTypeCreator;import migration_files_creator.static_query_creator.ParameterTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import unit.static_query_creator.BaseQueryValidationTest;
import java.io.*;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class ModelTypeBuildQueryTest extends BaseQueryValidationTest {

  @Autowired
  private ModelTypeCreator modelTypeCreator;

  private static final String FILE_NAME = "src/test/java/integration/migration_file.sql";

  @Test
  public void testParameterTypeQuery() {
    modelTypeCreator.createStaticTable(FILE_NAME);
    File file = new File(FILE_NAME);
    Assertions.assertTrue(file.exists() && file.length() > 0);
    validateContent();
    file.delete();
  }

  private void validateContent(){
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
      String firstLine = br.readLine();
      Assertions.assertTrue(firstLine.contains("INSERT INTO model_type(name) VALUES ('Statistical Model test');"));
    } catch (IOException e) {
      System.err.println("Error reading the file: " + e.getMessage());
    }
  }

}
