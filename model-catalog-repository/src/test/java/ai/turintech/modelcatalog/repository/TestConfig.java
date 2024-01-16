package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackageClasses = {ModelCatalogRepositoryPackage.class})
@ComponentScan(basePackageClasses = {ModelCatalogRepositoryPackage.class})
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class})
public class TestConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl(MetricRepositoryTest.postgreSQLContainer.getJdbcUrl());
    dataSource.setUsername(MetricRepositoryTest.postgreSQLContainer.getUsername());
    dataSource.setPassword(MetricRepositoryTest.postgreSQLContainer.getPassword());
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan(
        "ai.turintech.modelcatalog.entity"); // Package where your JPA entities are located
    // Additional JPA configuration can be added here
    JpaVendorAdapter vendorAdapter =
        new HibernateJpaVendorAdapter(); // or any other JPA vendor adapter
    em.setJpaVendorAdapter(vendorAdapter);
    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
