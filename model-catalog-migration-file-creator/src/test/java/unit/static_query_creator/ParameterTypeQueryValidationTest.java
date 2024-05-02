package unit.static_query_creator;

import java.util.Set;
import migrationfilescreator.staticquerycreator.ParameterTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterTypeQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery = "INSERT INTO parameter_type(name) VALUES ('test');";
    String expectedAuditQuery =
        "INSERT INTO parameter_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from parameter_type where name = 'test'), (select max(rev) from revinfo),0, 'test',";
    Set<String> parameterTypes = Set.of("test");
    String actualQuery = ParameterTypeCreator.buildParameterTypeSQL(parameterTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM parameter_type WHERE name='test';";
    String expectedAuditQuery =
        "INSERT INTO parameter_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from parameter_type where name = 'test'), (select max(rev) from revinfo),2, null,";
    Set<String> parameterTypes = Set.of("test");
    String deleteQuery = ParameterTypeCreator.buildDeleteParameterTypeSQL(parameterTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
