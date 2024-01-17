package ai.turintech.modelcatalog.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ConnectionTest extends BasicRepositoryTest {
  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  void testDatabaseConnection() {
    // Assuming there's a users table in your schema.sql
    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metric", Integer.class);
    System.out.println("Count: " + count);
    assertNotNull(count, "Count should not be null");
    }
}
