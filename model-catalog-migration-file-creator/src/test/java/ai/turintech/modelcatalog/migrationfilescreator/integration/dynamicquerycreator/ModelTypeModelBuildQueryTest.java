package ai.turintech.modelcatalog.migrationfilescreator.integration.dynamicquerycreator;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.model.Metadata;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.pivottables.ModelTypePivot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class ModelTypeModelBuildQueryTest {

  private static Model model;
  private static List<ModelDTO> dbModelList;

  private static ModelDTO modelDTO;

  private static final String EXPECTED_OUTPUT_INSERT_PATH =
      "src/test/resources/expectedoutput/integration_model_type_pivot_table_insert.txt";
  private static final String EXPECTED_OUTPUT_DELETE_PATH =
      "src/test/resources/expectedoutput/integration_model_type_pivot_table_delete.txt";
  @Autowired private ModelTypePivot modelTypePivot;

  @BeforeAll
  public static void setUp() {
    Metadata metadata = new Metadata();
    metadata.setModelType(List.of("modelType_new"));
    model = new Model();
    model.setName("logistic_regression_classifier");
    model.setMetadata(metadata);

    modelDTO = new ModelDTO();
    modelDTO.setName("logistic_regression_classifier");
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setName("modelType");
    modelDTO.setTypes(Set.of(modelTypeDTO));
    dbModelList = List.of(modelDTO);
  }

  @Test
  public void queryBuilderInsertTest() {
    String insertQuery = modelTypePivot.buildInsertIntoPivotSQL(model, dbModelList);
    validateContent(insertQuery, EXPECTED_OUTPUT_INSERT_PATH);
  }

  @Test
  public void queryBuilderDeleteTest() {
    String deleteQuery = modelTypePivot.buildDeleteSQLPivotTableNotExist(modelDTO, model);
    validateContent(deleteQuery, EXPECTED_OUTPUT_DELETE_PATH);
  }

  public void validateContent(String insertQuery, String expectedOutput) {
    try (BufferedReader br =
        new BufferedReader(new FileReader(expectedOutput, Charset.defaultCharset()))) {
      String line;
      while ((line = br.readLine()) != null) {
        Assertions.assertTrue(insertQuery.contains(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
