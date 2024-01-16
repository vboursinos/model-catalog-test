package ai.turintech.modelcatalog.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class MetricControllerTest {

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
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metrics", Integer.class);
    System.out.println("Count: " + count);
    assertNotNull(count, "Count should not be null");
  }

  @Test
  void saveUser_shouldPersistUserInDatabase() {

    System.out.println("Junit 5 test container example");
  }

  // Your test methods go here...
}
