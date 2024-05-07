package ai.turintech.modelcatalog.migrationfilescreator.integration.dynamicquerycreator;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.migrationfilescreator.integration.TestConfig;
import ai.turintech.modelcatalog.migrationfilescreator.model.EnsembleFamily;
import ai.turintech.modelcatalog.migrationfilescreator.model.Metadata;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.delete.DeleteDynamicTables;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.InsertModelTable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
public class ModelBuildQueryTest {

  private static Model model;
  private static List<ModelDTO> dbModelList;

  private static ModelDTO modelDTO;

  private static Models models;

  private static final String EXPECTED_OUTPUT_INSERT_PATH =
      "src/test/resources/expectedoutput/integration_model_table_insert.txt";
  private static final String EXPECTED_OUTPUT_DELETE_PATH =
      "src/test/resources/expectedoutput/integration_model_table_delete.txt";

  private static final String EXPECTED_OUTPUT_UPDATE_PATH =
      "src/test/resources/expectedoutput/integration_model_table_update.txt";
  @Autowired private InsertModelTable insertModelTable;

  @Autowired private DeleteDynamicTables deleteDynamicTables;

  @BeforeAll
  public static void setUp() {
    String[] advantages = {"test_advantages"};
    String[] disadvantages = {"test_disadvantages"};

    Metadata metadata = new Metadata();
    metadata.setAdvantages(List.of("test_advantages"));
    metadata.setDisadvantages(List.of("test_disadvantages"));
    metadata.setModelDescription("test_description");
    metadata.setDisplayName("logistic regression classifier");
    metadata.setStructure("test_structure");
    metadata.setDependencyGroup("test_dependency_group");
    metadata.setModelType(List.of("s"));

    model = new Model();
    model.setName("logistic_regression_classifier");
    model.setMlTask("classification");
    model.setBlackListed(false);
    model.setMetadata(metadata);
    models = new Models();
    models.setModels(List.of(model));

    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setName("classification");
    mlTaskTypeDTO.setId(UUID.fromString("4805854d-1578-442f-9329-9392add8b7c7"));
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setName("test_structure");
    modelStructureTypeDTO.setId(UUID.fromString("4805854d-1578-442f-9329-9392add8b7c8"));
    DependencyGroupTypeDTO dependencyGroupTypeDTO = new DependencyGroupTypeDTO();
    dependencyGroupTypeDTO.setName("test_dependency_group");
    dependencyGroupTypeDTO.setId(UUID.fromString("4805854d-1578-442f-9329-9392add8b7c9"));
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setName("linear");
    modelFamilyTypeDTO.setId(UUID.fromString("4805854d-1578-442f-9329-9392add8b7c0"));
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setName("none");
    modelEnsembleTypeDTO.setId(UUID.fromString("4805854d-1578-442f-9329-9392add8b7c1"));
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setName("s");

    modelDTO = new ModelDTO();
    modelDTO.setName("logistic_regression_classifier");
    modelDTO.setDisplayName("logistic regression classifier");
    modelDTO.setDescription("test_description");
    modelDTO.setMlTask(mlTaskTypeDTO);
    modelDTO.setStructure(modelStructureTypeDTO);
    modelDTO.setAdvantages(advantages);
    modelDTO.setDisadvantages(disadvantages);
    modelDTO.setEnabled(true);
    modelDTO.setDecisionTree(false);
    modelDTO.setFamilyType(modelFamilyTypeDTO);
    modelDTO.setEnsembleType(modelEnsembleTypeDTO);
    modelDTO.setDependencyGroupType(dependencyGroupTypeDTO);
    modelDTO.setTypes(Set.of(modelTypeDTO));
    dbModelList = List.of(modelDTO);
  }

  @Test
  public void isDecisionTree() {
    boolean isDecisionTree = insertModelTable.isDecisionTreeModel(modelDTO.getName());
    System.out.println(isDecisionTree);
    Assertions.assertTrue(!isDecisionTree);
  }

  @Test
  public void FamilyEnsembleTest() {
    EnsembleFamily ensembleFamily = insertModelTable.getEnsembleFamily(modelDTO.getName());
    Assertions.assertEquals("linear", ensembleFamily.getFamily());
    Assertions.assertEquals("none", ensembleFamily.getEnsembleType());
  }

  @Test
  public void testQueryBuilderWithoutChange() {
    model.setName("logistic_regression_classifier");
    modelDTO.setName("logistic_regression_classifier");
    String insertQuery = insertModelTable.buildInsertIntoModelSQL(model, dbModelList);
    System.out.println(insertQuery);
    Assertions.assertEquals("\n", insertQuery);
  }

  @Test
  public void testQueryBuilderInsert() {
    model.setName("logistic_regression_classifier_1");
    modelDTO.setName("logistic_regression_classifier");
    String insertQuery = insertModelTable.buildInsertIntoModelSQL(model, dbModelList);
    System.out.println(insertQuery);
    validateContent(insertQuery, EXPECTED_OUTPUT_INSERT_PATH);
  }

  @Test
  public void testQueryBuilderUpdate() {
    model.setName("logistic_regression_classifier");
    model.getMetadata().setDisplayName("logistic regression classifier_test");
    modelDTO.setName("logistic_regression_classifier");
    String insertQuery = insertModelTable.buildInsertIntoModelSQL(model, dbModelList);
    System.out.println(insertQuery);
    validateContent(insertQuery, EXPECTED_OUTPUT_UPDATE_PATH);
  }

  @Test
  public void testQueryBuilderDelete() {
    modelDTO.setName("logistic_regression_classifier_test");
    model.setName("logistic_regression_classifier");
    String deleteQuery = deleteDynamicTables.buildDeleteSQL(model.getMlTask(), models, dbModelList);
    System.out.println(deleteQuery);
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
