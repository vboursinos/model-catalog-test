package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.Metric;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class MetricRepositoryTest extends BasicRepositoryTest {
  @Autowired private MetricRepository metricRepository;

  private Metric getMetric() {
    Metric metric = new Metric();
    metric.setMetric("test_metric");
    return metric;
  }

  private Metric getUpdatedMetric() {
    Metric metric = new Metric();
    metric.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));
    metric.setMetric("test_updated_metric");
    return metric;
  }

  @Test
  void testFindAllMetricRepository() {
    System.out.println("MetricRepository: " + metricRepository);
    List<Metric> metrics = metricRepository.findAll();
    System.out.println("Metrics: " + metrics);
    Assertions.assertEquals(4, metrics.size());
  }

  @Test
  void testFindByIdMetricRepository() {
    System.out.println("MetricRepository: " + metricRepository);
    Metric metric =
        metricRepository.findById(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23")).get();
    System.out.println("Metric: " + metric);
    Assertions.assertEquals("Metric1", metric.getMetric());
  }

  @Test
  void testSaveMetricRepository() {
    System.out.println("MetricRepository: " + metricRepository);
    Metric savedMetric = metricRepository.save(getMetric());
    System.out.println("Saved Metric: " + savedMetric);
    Assertions.assertEquals(getMetric().getMetric(), savedMetric.getMetric());
    metricRepository.delete(savedMetric);
  }

  @Test
  void testUpdateMetricRepository() {
    System.out.println("MetricRepository: " + metricRepository);
    Metric savedMetric = metricRepository.save(getUpdatedMetric());
    System.out.println("Saved Metric: " + savedMetric);
    Assertions.assertEquals(getUpdatedMetric().getMetric(), savedMetric.getMetric());
  }
}
