package ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator;

import ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.ParameterDistributionTypeCreator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterDistributionTypeQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery = "INSERT INTO parameter_distribution_type(name) VALUES ('test');";
    String expectedAuditQuery =
        "INSERT INTO parameter_distribution_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from parameter_distribution_type where name = 'test'), (select max(rev) from revinfo),0, 'test',";
    Set<String> parameterDistributionTypes = Set.of("test");
    String actualQuery =
        ParameterDistributionTypeCreator.buildParameterDistributionTypeSQL(
            parameterDistributionTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM parameter_distribution_type WHERE name='test';";
    String expectedAuditQuery =
        "INSERT INTO parameter_distribution_type_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from parameter_distribution_type where name = 'test'), (select max(rev) from revinfo),2, null,";
    Set<String> parameterDistributionTypes = Set.of("test");
    String deleteQuery =
        ParameterDistributionTypeCreator.buildDeleteParameterDistributionTypeSQL(
            parameterDistributionTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
