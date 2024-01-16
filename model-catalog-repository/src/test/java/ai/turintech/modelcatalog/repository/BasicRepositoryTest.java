package ai.turintech.modelcatalog.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class BasicRepositoryTest {
  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:latest")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpass")
          .withInitScript("sql/schema.sql");

  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  void testDatabaseConnection() {
    // Assuming there's a users table in your schema.sql
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metric", Integer.class);
    System.out.println("Count: " + count);
    assertNotNull(count, "Count should not be null");
  }
}
