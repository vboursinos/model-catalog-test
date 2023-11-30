package ai.turintech.modelcatalog.rest.indicators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PostgresHealthIndicator implements HealthIndicator {
  @Value("${spring.datasource.database-name}")
  private String database;

  @Value("${spring.datasource.host}")
  private String host;

  @Value("${spring.datasource.port}")
  private Integer port;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Override
  public Health health() {
    String connectionString = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
    try (Connection connection =
        DriverManager.getConnection(connectionString, username, password)) {
      if (connection != null) {
        return Health.up().build();
      } else {
        return Health.down().build();
      }
    } catch (SQLException e) {
      return Health.down().withDetail("Error Code", e.getErrorCode()).build();
    }
  }
}
