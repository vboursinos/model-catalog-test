package ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.GroupTypeCreator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupTypeQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery = "INSERT INTO model_group_type(name) VALUES ('Group');";
    String expectedAuditQuery =
        "INSERT INTO model_group_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_group_type where name = 'Group'), (select max(rev) from revinfo),0, 'Group',";
    Set<String> modelGroupTypes = Set.of("Group");
    String actualQuery = GroupTypeCreator.buildInsertGroupTypeSQL(modelGroupTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM model_group_type WHERE name='Group';";
    String expectedAuditQuery =
        "INSERT INTO model_group_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_group_type where name = 'Group'), (select max(rev) from revinfo),2, null,";
    Set<String> modelTypes = Set.of("Group");
    String deleteQuery = GroupTypeCreator.buildDeleteGroupTypeSQL(modelTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}