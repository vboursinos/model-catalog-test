package ai.turintech.modelcatalog.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ai.turintech.modelcatalog.entity.Metric;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class MetricRepositoryTest {

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:latest")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpass")
          .withInitScript("sql/schema.sql");

  @Autowired private JdbcTemplate jdbcTemplate;

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
  void testDatabaseConnection() {
    // Assuming there's a users table in your schema.sql
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metric", Integer.class);
    System.out.println("Count: " + count);
    assertNotNull(count, "Count should not be null");
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
