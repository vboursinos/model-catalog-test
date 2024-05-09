package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicquerycreator;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.pivottables.GroupPivot;
import ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator.BaseQueryValidationTest;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GroupModelQueryValidationTest extends BaseQueryValidationTest {

  private static Model model;
  private static List<ModelDTO> dbModelList;

  private static ModelGroupTypeDTO modelGroupTypeDTO;

  private static ModelDTO modelDTO;
  private GroupPivot groupPivot = new GroupPivot();

  @BeforeAll
  public static void setUp() {
    model = new Model();
    model.setName("logistic_regression_classifier");
    model.setGroups(List.of("test"));

    modelDTO = new ModelDTO();
    modelDTO.setName("logistic_regression_classifier");
    modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setName("s");
    modelDTO.setGroups(Set.of(modelGroupTypeDTO));
    dbModelList = List.of(modelDTO);
  }

  @Test
  public void insertBuildQuery() {
    String expectedInsertQuery =
        "INSERT INTO rel_model__groups(model_id, group_id) VALUES ((select id from Model where name='logistic_regression_classifier'),(select id from model_group_type where name='test'));\n";
    String actualQuery =
        String.valueOf(
            groupPivot.createPivotQuerySQL(model.getGroups().get(0), modelDTO.getName()));
    Assertions.assertEquals(expectedInsertQuery, actualQuery);
  }

  @Test
  public void deleteBuildQueryWithGroup() {
    String expectedDeleteQuery =
        "DELETE FROM rel_model__groups WHERE group_id=(select id from model_group_type where name='s') AND model_id=(select id from model where name='logistic_regression_classifier');\n";
    String actualQuery = groupPivot.createDeletePivotQuerySQL(modelGroupTypeDTO, modelDTO);
    Assertions.assertEquals(expectedDeleteQuery, actualQuery);
  }

  @Test
  public void deleteBuildQueryWithoutGroup() {
    String expectedDeleteQuery =
        "DELETE FROM rel_model__groups WHERE model_id=(select id from model where name='logistic_regression_classifier');\n";
    String actualQuery = groupPivot.createDeletePivotQuerySQL(modelDTO);
    Assertions.assertEquals(expectedDeleteQuery, actualQuery);
  }

  @Test
  public void insertBuildQueryAUD() {
    String expectedInsertQuery =
        "INSERT INTO rel_model__groups_AUD(model_id, group_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='logistic_regression_classifier'),(select id from model_group_type where name='s'),1, (select max(rev) from revinfo)";
    String actualQuery =
        groupPivot.createPivotQueryAuditSQL(modelGroupTypeDTO.getName(), modelDTO.getName(), 1);
    Assertions.assertTrue(actualQuery.contains(expectedInsertQuery));
  }
}
