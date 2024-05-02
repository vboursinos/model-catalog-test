package ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator;

import java.util.Map;
import java.util.Set;
import ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.DependencyGroupTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DependencyTypeQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery =
        "INSERT INTO dependency_type(name,dependency_group_id) VALUES ('test',(select id from dependency_group_type where name='test_key'));";
    String expectedAuditQuery =
        "INSERT INTO dependency_type_aud(id, rev, revtype, name, dependency_group_id, created_at, updated_at) VALUES ((select id from dependency_type where name = 'test'), (select max(rev) from revinfo),0, 'test', (select id from dependency_group_type where name = 'test_key'),";
    Set<String> dependencyTypes = Set.of("test");
    Map<String, Set<String>> dependencyTypeMap = Map.of("test_key", dependencyTypes);
    String actualQuery =
        DependencyGroupTypeCreator.buildInsertDependenciesTypeSQL(dependencyTypeMap);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM dependency_type WHERE name='test';";
    String expectedAuditQuery =
        "INSERT INTO dependency_type_aud(id, rev, revtype, name, dependency_group_id, created_at, updated_at) VALUES ((select id from dependency_type where name = 'test'), (select max(rev) from revinfo),2, 'test', (select id from dependency_group_type where name = 'test_key'),";
    Set<String> dependencyTypes = Set.of("test");
    Map<String, Set<String>> dependencyTypeMap = Map.of("test_key", dependencyTypes);
    String deleteQuery = DependencyGroupTypeCreator.buildDeleteDependencySQL(dependencyTypeMap);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
