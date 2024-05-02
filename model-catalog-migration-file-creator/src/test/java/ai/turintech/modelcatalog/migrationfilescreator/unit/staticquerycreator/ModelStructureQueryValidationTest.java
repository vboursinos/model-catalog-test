package ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.ModelStructureCreator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelStructureQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery =
        "INSERT INTO model_structure_type(name) VALUES ('ModelStructure');";
    String expectedAuditQuery =
        "INSERT INTO model_structure_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_structure_type where name = 'ModelStructure'), (select max(rev) from revinfo),0, 'ModelStructure',";
    Set<String> modelTypes = Set.of("ModelStructure");
    String actualQuery = ModelStructureCreator.buildInsertModelStructureTypeSQL(modelTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM model_structure_type WHERE name='ModelStructure';";
    String expectedAuditQuery =
        "INSERT INTO model_structure_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from model_structure_type where name = 'ModelStructure'), (select max(rev) from revinfo),2, null,";
    Set<String> modelTypes = Set.of("ModelStructure");
    String deleteQuery = ModelStructureCreator.buildDeleteModelStructureSQL(modelTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
