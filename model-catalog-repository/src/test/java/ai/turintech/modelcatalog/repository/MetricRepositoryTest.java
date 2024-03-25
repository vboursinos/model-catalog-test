package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.Metric;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class MetricRepositoryTest {
  @Autowired private MetricRepository metricRepository;

  private static final String METRIC_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23";
  private static final String NEW_METRIC_ID = "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26";

  private Metric getMetric() {
    Metric metric = new Metric();
    metric.setMetric("test_metric");
    return metric;
  }

  private Metric getUpdatedMetric() {
    Metric metric = new Metric();
    metric.setId(UUID.fromString(NEW_METRIC_ID));
    metric.setMetric("test_updated_metric");
    return metric;
  }

  @Test
  void testFindAllMetricRepository() {
    List<Metric> metrics = metricRepository.findAll();
    Assertions.assertEquals(4, metrics.size());
  }

  @Test
  void testFindByIdMetricRepository() {
    Metric metric = metricRepository.findById(UUID.fromString(METRIC_ID)).get();
    Assertions.assertEquals("Metric1", metric.getMetric());
  }

  @Test
  void testSaveMetricRepository() {
    Metric savedMetric = metricRepository.save(getMetric());
    Assertions.assertEquals(getMetric().getMetric(), savedMetric.getMetric());
    metricRepository.delete(savedMetric);
  }

  @Test
  void testUpdateMetricRepository() {
    Metric savedMetric = metricRepository.save(getUpdatedMetric());
    Assertions.assertEquals(getUpdatedMetric().getMetric(), savedMetric.getMetric());
  }
}
