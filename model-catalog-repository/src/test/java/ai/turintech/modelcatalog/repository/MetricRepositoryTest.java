package ai.turintech.modelcatalog.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ai.turintech.modelcatalog.entity.Metric;
import java.util.List;
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

  @Test
  void testDatabaseConnection() {
    // Assuming there's a users table in your schema.sql
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metric", Integer.class);
    System.out.println("Count: " + count);
    assertNotNull(count, "Count should not be null");
  }

  @Test
  void saveUser_shouldPersistUserInDatabase() {

    System.out.println("Junit 5 test container example");
  }

  @Test
  void testMetricRepository() {
    System.out.println("MetricRepository: " + metricRepository);
    List<Metric> metrics = metricRepository.findAll();
    System.out.println("Metrics: " + metrics);
    Assertions.assertEquals(4, metrics.size());
  }
  // Your test methods go here...
}
