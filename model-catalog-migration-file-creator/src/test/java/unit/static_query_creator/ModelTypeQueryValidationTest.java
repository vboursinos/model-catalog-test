package unit.static_query_creator;

import java.util.Set;
import migration_files_creator.static_query_creator.ModelTypeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelTypeQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery = "INSERT INTO model_type(name) VALUES ('ModelType1');";
    String expectedAuditQuery =
        "INSERT INTO model_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_type where name = 'ModelType1'), (select max(rev) from revinfo),0, 'ModelType1',";
    Set<String> modelTypes = Set.of("ModelType1");
    String actualQuery = ModelTypeCreator.buildInsertModelTypeSQL(modelTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM model_type WHERE name='ModelType1';";
    String expectedAuditQuery =
        "INSERT INTO model_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_type where name = 'ModelType1'), (select max(rev) from revinfo),2, null,";
    Set<String> modelTypes = Set.of("ModelType1");
    String deleteQuery = ModelTypeCreator.buildDeleteModelTypeSQL(modelTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
