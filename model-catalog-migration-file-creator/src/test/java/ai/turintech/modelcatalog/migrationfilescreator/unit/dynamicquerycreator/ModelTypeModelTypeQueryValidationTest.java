package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicquerycreator;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Metadata;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.pivottables.ModelTypePivot;
import ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator.BaseQueryValidationTest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ModelTypeModelTypeQueryValidationTest extends BaseQueryValidationTest {

  private static Model model;
  private static List<ModelDTO> dbModelList;

  private static ModelDTO modelDTO;
  private ModelTypePivot modelTypePivot = new ModelTypePivot();

  @BeforeAll
  public static void setUp() {
    model = new Model();
    model.setName("logistic_regression_classifier");
    List<String> modelTypes = Arrays.asList("test");
    Metadata metadata = new Metadata();
    metadata.setModelType(modelTypes);
    model.setMetadata(metadata);

    modelDTO = new ModelDTO();
    modelDTO.setName("logistic_regression_classifier");
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setName("s");
    modelDTO.setTypes(Set.of(modelTypeDTO));
    dbModelList = List.of(modelDTO);
  }

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery =
        "INSERT INTO rel_model__model_type(model_id, model_type_id) VALUES ((select id from Model where name='logistic_regression_classifier'),(select id from model_type where name='test'));";
    String expectedAuditQuery =
        "INSERT INTO rel_model__model_type_AUD(model_id, model_type_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='logistic_regression_classifier'),(select id from model_type where name='test'),0, (select max(rev) from revinfo),";

    String actualQuery = modelTypePivot.buildInsertIntoPivotSQL(model, dbModelList);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery =
        "DELETE FROM rel_model__model_type WHERE model_type_id=(select id from model_type where name='s') AND model_id=(select id from model where name='logistic_regression_classifier');";
    String expectedAuditQuery =
        "INSERT INTO rel_model__model_type_AUD(model_id, model_type_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='logistic_regression_classifier'),(select id from model_type where name='s'),2, (select max(rev) from revinfo),";
    String deleteQuery = modelTypePivot.buildDeleteSQLPivotTableNotExist(modelDTO, model);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
