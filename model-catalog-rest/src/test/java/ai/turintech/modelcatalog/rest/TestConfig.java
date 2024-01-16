package ai.turintech.modelcatalog.rest;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class TestConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl(MetricControllerTest.postgreSQLContainer.getJdbcUrl());
    dataSource.setUsername(MetricControllerTest.postgreSQLContainer.getUsername());
    dataSource.setPassword(MetricControllerTest.postgreSQLContainer.getPassword());
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
