package ai.turintech.modelcatalog.migrationfilescreator.unit.dynamicquerycreator;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.pivottables.MetricPivot;
import ai.turintech.modelcatalog.migrationfilescreator.unit.staticquerycreator.BaseQueryValidationTest;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MetricModelTypeQueryValidationTest extends BaseQueryValidationTest {

  private static Model model;
  private static List<ModelDTO> dbModelList;

  private static ModelDTO modelDTO;
  private MetricPivot metricPivot = new MetricPivot();

  @BeforeAll
  public static void setUp() {
    model = new Model();
    model.setName("logistic_regression_classifier");
    model.setIncompatibleMetrics(List.of("test"));

    modelDTO = new ModelDTO();
    modelDTO.setName("logistic_regression_classifier");
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setMetric("s");
    modelDTO.setIncompatibleMetrics(Set.of(metricDTO));
    dbModelList = List.of(modelDTO);
  }

  @Test
  public void validateInsertQuery() {
    String expectedInsertQuery =
        "INSERT INTO rel_model__incompatible_metrics(model_id, metric_id) VALUES ((select id from model where name='logistic_regression_classifier'), (select id from metric where name='test'));";
    String expectedAuditQuery =
        "INSERT INTO rel_model__incompatible_metrics_AUD(model_id, metric_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='logistic_regression_classifier'),(select id from metric where name='test'),0, (select max(rev) from revinfo),";

    String actualQuery = metricPivot.buildInsertIntoPivotSQL(model, dbModelList);
    String[] sqlStatements = actualQuery.split("\n");
    Assertions.assertEquals(expectedInsertQuery, sqlStatements[0]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[1].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[2].startsWith(expectedAuditQuery));
  }

  @Test
  public void validateDeleteQuery() {
    String expectedDeleteQuery =
        "DELETE FROM rel_model__incompatible_metrics WHERE metric_id=(select id from metric where name='s') AND model_id=(select id from model where name='logistic_regression_classifier');";
    String expectedAuditQuery =
        "INSERT INTO rel_model__incompatible_metrics_AUD(model_id, metric_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='logistic_regression_classifier'),(select id from metric where name='s'),2, (select max(rev) from revinfo),";
    String deleteQuery = metricPivot.buildDeleteSQLPivotTableNotExist(modelDTO, model);
    String[] sqlStatements = deleteQuery.split("\n");
    Assertions.assertEquals(expectedDeleteQuery, sqlStatements[2]);
    Assertions.assertEquals(3, sqlStatements.length);
    Assertions.assertTrue(sqlStatements[0].startsWith(EXPECTED_REV_INFO_QUERY));
    Assertions.assertTrue(sqlStatements[1].startsWith(expectedAuditQuery));
  }
}
