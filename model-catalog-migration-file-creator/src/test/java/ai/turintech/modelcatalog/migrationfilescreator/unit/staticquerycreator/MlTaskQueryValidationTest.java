package ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.MlTaskCreator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MlTaskQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery = "INSERT INTO ml_task_type(name) VALUES ('MlTaskType1');";
    String expectedAuditQuery =
        "INSERT INTO ml_task_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from ml_task_type where name = 'MlTaskType1'), (select max(rev) from revinfo),0, 'MlTaskType1',";
    Set<String> modelTypes = Set.of("MlTaskType1");
    String actualQuery = MlTaskCreator.buildMlTaskTypesSQL(modelTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM ml_task_type WHERE name='MlTaskType1';";
    String expectedAuditQuery =
        "INSERT INTO ml_task_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from ml_task_type where name = 'MlTaskType1'), (select max(rev) from revinfo),2, null,";
    Set<String> modelTypes = Set.of("MlTaskType1");
    String deleteQuery = MlTaskCreator.buildDeleteMlTaskTypesSQL(modelTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
