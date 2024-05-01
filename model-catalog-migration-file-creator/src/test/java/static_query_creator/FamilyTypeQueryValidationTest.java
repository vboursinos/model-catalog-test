package static_query_creator;

import java.util.Set;
import migration_files_creator.static_query_creator.EnsembleFamilyCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FamilyTypeQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery = "INSERT INTO model_family_type(name) VALUES ('test');";
    String expectedAuditQuery =
        "INSERT INTO model_family_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_family_type where name = 'test'), (select max(rev) from revinfo),0, 'test',";
    Set<String> parameterTypes = Set.of("test");
    String actualQuery = EnsembleFamilyCreator.buildInsertFamilyTypeSQL(parameterTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM model_family_type WHERE name='test';";
    String expectedAuditQuery =
        "INSERT INTO model_family_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_family_type where name = 'test'), (select max(rev) from revinfo),2, null,";
    Set<String> parameterTypes = Set.of("test");
    String deleteQuery = EnsembleFamilyCreator.buildDeleteFamilyTypeSQL(parameterTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
