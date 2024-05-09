package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicquerycreator;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.migrationfilescreator.model.EnsembleFamily;
import ai.turintech.modelcatalog.migrationfilescreator.model.Metadata;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.InsertModelTableImpl;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.ModelTableBuilder;
import ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator.BaseQueryValidationTest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ModelQueryValidationTest extends BaseQueryValidationTest {

  private static Model model;
  private static ModelDTO modelDTO;

  private static EnsembleFamily ensembleFamily;
  private InsertModelTableImpl insertModelTable = new InsertModelTableImpl();

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
    Models models;
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

    ensembleFamily = new EnsembleFamily();
    ensembleFamily.setFamily("linear");
    ensembleFamily.setEnsembleType("none");
  }

  @Test
  public void normalizeDescriptionTest() {
    String description = "test_description\n";
    String expected = "test_description";
    String actual = insertModelTable.normalizeDescription(description);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void buildArrayTest() {
    List<String> modelList = Arrays.asList("test1", "test2", "test3");
    String expected = "{test1,test2,test3}";
    String actual = insertModelTable.buildArray(modelList);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void normalizeStringTest() {
    String description = "''test''_test_description\n";
    String expected = "'test'_test_description";
    String actual = InsertModelTableImpl.normalizeString(description);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void normalizeListTest() {
    List<String> list = Arrays.asList("'test1'", "'test2'");
    String expected = "[''test1'', ''test2'']";
    String actual = InsertModelTableImpl.normalizeList(list);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void modelUpdateQueryTest() {
    String query = ModelTableBuilder.updateModelSQL(model, ensembleFamily, false);
    String expectedUpdateQuery =
        "UPDATE model SET ml_task_id=(select id from ml_task_type where name='classification'), description='test_description', display_name='logistic regression classifier', structure_id=(select id from model_structure_type where name='test_structure'), advantages='{test_advantages}', disadvantages='{test_disadvantages}', enabled=true, ensemble_type_id=(select id from model_ensemble_type where name='none'), family_type_id=(select id from model_family_type where name='linear'), decision_tree=false, dependency_group_id=(select id from dependency_group_type where name='test_dependency_group') WHERE name='logistic_regression_classifier';\n";
    Assertions.assertTrue(query.contains(expectedUpdateQuery));
  }

  @Test
  public void modelInsertQueryTest() {
    String advantagesArray = "{test_advantages}";
    String disadvantagesArray = "{test_disadvantages}";
    String description =
        InsertModelTableImpl.normalizeString(model.getMetadata().getModelDescription());
    String query =
        ModelTableBuilder.insertModelSQL(
            model, ensembleFamily, advantagesArray, disadvantagesArray, description, false);
    String expectedInsertQuery =
        "INSERT INTO model(name, ml_task_id, description, display_name, structure_id, advantages, disadvantages, enabled, ensemble_type_id, family_type_id, decision_tree, dependency_group_id) VALUES ('logistic_regression_classifier', (select id from ml_task_type where name='classification'),'test_description', 'logistic regression classifier', (select id from model_structure_type where name='test_structure'), '{test_advantages}', '{test_disadvantages}', true, (select id from model_ensemble_type where name='none'),(select id from model_family_type where name='linear'), false, (select id from dependency_group_type where name='test_dependency_group'));\n";
    Assertions.assertEquals(expectedInsertQuery, query);
  }

  @Test
  public void modelInsertQueryAUDTest() {
    String query =
        ModelTableBuilder.insertModelAuditSQL(model, ensembleFamily, false, 0).toString();
    String expectedAuditQuery =
        "INSERT INTO model_aud(id, rev, revtype, created_at, updated_at, ml_task_id, name, display_name, structure_id, description, advantages, disadvantages, enabled, ensemble_type_id, family_type_id, decision_tree, dependency_group_id) VALUES ((select id from model where name = 'logistic_regression_classifier'), (select max(rev) from revinfo),0,";
    Assertions.assertTrue(query.contains(expectedAuditQuery));
  }

  @Test
  public void modelDeleteQueryAUDTest() {
    String query = ModelTableBuilder.insertModelAuditSQLDelete(modelDTO).toString();
    String expectedAuditQuery =
        "INSERT INTO model_aud(id, rev, revtype, created_at, updated_at, ml_task_id, name, display_name, structure_id, description, advantages, disadvantages, enabled, ensemble_type_id, family_type_id, decision_tree, dependency_group_id) VALUES ((select id from model where name = 'logistic_regression_classifier'), (select max(rev) from revinfo), 2,";
    Assertions.assertTrue(query.contains(expectedAuditQuery));
  }
}
