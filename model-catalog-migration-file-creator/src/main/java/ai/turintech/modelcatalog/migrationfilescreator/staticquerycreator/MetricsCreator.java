package ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import ai.turintech.modelcatalog.service.MetricService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MetricsCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(MetricsCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Value("${json_dir_path}")
  private String jsonDirPath;

  @Autowired private MetricService metricService;

  public void createStaticTable(String newFileName) {
    Set<String> allMetrics = new HashSet<>();
    try {
      allMetrics = extractAllMetrics();
      List<MetricDTO> metrics = metricService.findAll().block();
      logger.info("Metrics: " + metrics);
      compareMetrics(allMetrics, metrics, newFileName);
    } catch (IOException e) {
      logger.error("Error while creating metrics: " + e.getMessage());
    }
  }

  private Set<String> extractAllMetrics() throws IOException {
    Path dirPath = Paths.get(jsonDirPath);
    InsertStaticTables insertStaticTables = new InsertStaticTables();
    Set<String> allMetrics =
        insertStaticTables.extractUniqueValues(mapper, dirPath, MetricsCreator::getMetrics);
    return allMetrics;
  }

  private void compareMetrics(Set<String> allMetrics, List<MetricDTO> metrics, String newFileName) {
    Set<String> metricsForDeletion = new HashSet<>();
    Set<String> foundMetrics = new HashSet<>();
    for (MetricDTO metric : metrics) {
      if (allMetrics.contains(metric.getMetric())) {
        logger.info("Metric found: " + metric.getMetric());
        foundMetrics.add(metric.getMetric());
      } else {
        logger.info("Metric not found: " + metric.getMetric());
        metricsForDeletion.add(metric.getMetric());
      }
    }
    if (metricsForDeletion.size() > 0) {
      logger.info("Metric for deletion: " + metricsForDeletion);
      insertStaticTables.createSQLFile(newFileName, buildDeleteMetricSQL(metricsForDeletion), true);
    }
    allMetrics.removeAll(foundMetrics);
    if (allMetrics.size() > 0) {
      logger.info("Metric for insertion: " + allMetrics);
      insertStaticTables.createSQLFile(newFileName, buildMetricSQL(allMetrics), true);
    }
  }

  public static String buildMetricSQL(Set<String> metricSet) {
    StringBuilder sb = new StringBuilder();
    for (String metric : metricSet) {
      sb.append("INSERT INTO metric(name) VALUES ('").append(metric).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(metric, "metric", 0));
    }
    return sb.toString();
  }

  public static String buildDeleteMetricSQL(Set<String> metrics) {
    StringBuilder sb = new StringBuilder();
    for (String metric : metrics) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(metric, "metric", 2));
      sb.append("DELETE FROM metric WHERE name='").append(metric).append("';\n");
    }
    return sb.toString();
  }

  public static Set<String> getMetrics(Models models) {
    Set<String> metrics = new HashSet<>();
    for (Model model : models.getModels()) {
      List<String> incompatibleMetrics = model.getIncompatibleMetrics();
      if (incompatibleMetrics != null && !incompatibleMetrics.isEmpty()) {
        metrics.addAll(incompatibleMetrics);
      }
    }
    return metrics;
  }
}
