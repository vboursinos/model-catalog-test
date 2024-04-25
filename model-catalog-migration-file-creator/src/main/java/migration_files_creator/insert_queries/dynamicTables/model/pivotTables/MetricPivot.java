package migration_files_creator.insert_queries.dynamicTables.model.pivotTables;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import migration_files_creator.insert_queries.staticTables.TableCreatorHelper;
import migration_files_creator.model.Model;
import org.springframework.stereotype.Component;

@Component
public class MetricPivot implements Pivot {

  public String buildInsertIntoPivotSQL(Model model, List<ModelDTO> dbModelList) {
    StringBuilder sb = new StringBuilder();
    boolean found = false;
    for (ModelDTO dbModel : dbModelList) {
      if (model.getName().equals(dbModel.getName())) {
        found = true;
        for (String metric : model.getIncompatibleMetrics()) {
          if (dbModel.getIncompatibleMetrics().stream()
              .noneMatch(g -> g.getMetric().equals(metric))) {
            sb.append(createPivotQuerySQL(metric, model.getName()));
            sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
            sb.append(createPivotQueryAuditSQL(metric, model.getName(), 0));
          }
        }
      }
    }
    if (!found) {
      for (String metric : model.getIncompatibleMetrics()) {
        sb.append(createPivotQuerySQL(metric, model.getName()));
        sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
        sb.append(createPivotQueryAuditSQL(metric, model.getName(), 0));
      }
    }
    return sb + "\n";
  }

  public String buildDeleteSQLPivotTableNotExist(
      ModelDTO dbModel, migration_files_creator.model.Model jsonModel) {
    StringBuilder sb = new StringBuilder();
    List<MetricDTO> incompatibleMetricsForDeletion =
        new ArrayList<>(dbModel.getIncompatibleMetrics());
    if (jsonModel.getName().equals(dbModel.getName())) {
      for (MetricDTO incompatibleMetric : dbModel.getIncompatibleMetrics()) {
        for (String jsonIncompatibleMetric : jsonModel.getIncompatibleMetrics()) {
          if (incompatibleMetric.getMetric().equals(jsonIncompatibleMetric)) {
            incompatibleMetricsForDeletion.remove(incompatibleMetric);
          }
        }
      }
      for (MetricDTO incompatibleMetric : incompatibleMetricsForDeletion) {
        sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
        sb.append(createPivotQueryAuditSQL(incompatibleMetric.getMetric(), dbModel.getName(), 2));
        sb.append(createDeletePivotQuerySQL(incompatibleMetric, dbModel));
      }
    }
    return sb.toString();
  }

  public String buildInsertIntoPivotDeleteSQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    for (MetricDTO metric : model.getIncompatibleMetrics()) {
      sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
      sb.append(createPivotQueryAuditSQL(metric.getMetric(), model.getName(), 2));
    }
    return sb.toString();
  }

  public StringBuilder createPivotQuerySQL(String value, String modelName) {
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO rel_model__incompatible_metrics(model_id, metric_id) VALUES ((select id from model where name='")
        .append(modelName)
        .append("'), (select id from metric where name='")
        .append(value)
        .append("'));\n");
  }

  public String createDeletePivotQuerySQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append(
            "DELETE FROM rel_model__incompatible_metrics WHERE model_id=(select id from model where name='")
        .append(model.getName())
        .append("');\n");
    return sb.toString();
  }

  private static String createDeletePivotQuerySQL(MetricDTO metric, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append(
            "DELETE FROM rel_model__incompatible_metrics WHERE metric_id=(select id from metric where name='")
        .append(metric.getMetric())
        .append("') AND model_id=(select id from model where name='")
        .append(model.getName())
        .append("');\n");
    return sb.toString();
  }

  public String createPivotQueryAuditSQL(String value, String modelName, int action) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO rel_model__incompatible_metrics_AUD(model_id, metric_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='")
        .append(modelName)
        .append("'),")
        .append("(select id from metric where name='")
        .append(value)
        .append("'),")
        .append(action)
        .append(", (select max(rev) from revinfo), '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n")
        .toString();
  }

  public String getTableName() {
    return "rel_model__incompatible_metrics";
  }
}
