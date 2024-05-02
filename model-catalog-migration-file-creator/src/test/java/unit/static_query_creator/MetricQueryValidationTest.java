package unit.static_query_creator;

import java.util.Set;
import migrationfilescreator.staticquerycreator.MetricsCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetricQueryValidationTest extends BaseQueryValidationTest {

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery = "INSERT INTO metric(name) VALUES ('Metric');";
    String expectedAuditQuery =
        "INSERT INTO metric_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from metric where name = 'Metric'), (select max(rev) from revinfo),0, 'Metric',";
    Set<String> modelTypes = Set.of("Metric");
    String actualQuery = MetricsCreator.buildMetricSQL(modelTypes);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery = "DELETE FROM metric WHERE name='Metric';";
    String expectedAuditQuery =
        "INSERT INTO metric_aud(id, rev, revtype, name, created_at, updated_at) VALUES ((select id from metric where name = 'Metric'), (select max(rev) from revinfo),2, null,";
    Set<String> modelTypes = Set.of("Metric");
    String deleteQuery = MetricsCreator.buildDeleteMetricSQL(modelTypes);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
