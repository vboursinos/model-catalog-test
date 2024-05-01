package integration;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.jpa.search.JpaSearchPackage;
import ai.turintech.components.jpa.search.data.entity.JpaSearchEntityPackage;
import ai.turintech.components.jpa.search.repository.JpaSearchRepositoryPackage;
import ai.turintech.modelcatalog.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import ai.turintech.modelcatalog.service.ModelCatalogServicePackage;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import migration_files_creator.MigrationFilesCreatorPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;import java.io.IOException;import java.nio.file.Files;import java.nio.file.Paths;import java.util.Properties;

@Configuration
@EnableJpaRepositories(
    basePackageClasses = {ModelCatalogRepositoryPackage.class, JpaSearchRepositoryPackage.class})
@ComponentScan(
    basePackageClasses = {
      ModelCatalogRepositoryPackage.class,
      MigrationFilesCreatorPackage.class,
      ModelCatalogDtoEntityMapperPackage.class,
      ModelCatalogServicePackage.class,
      JpaSearchPackage.class,
      ReactiveAbstractUUIDIdentityCrudCallableImpl.class
    })
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class, JpaSearchEntityPackage.class})
@PropertySource("classpath:configuration.properties")
public class TestConfig {
  private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    private static final String PROPERTIES_FILE_PATH =
        "src/test/resources/configuration.properties";

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(DRIVER_CLASS_NAME);
    dataSource.setUrl(SingletonPostgresContainer.getInstance().getJdbcUrl());
    dataSource.setUsername(SingletonPostgresContainer.getInstance().getUsername());
    dataSource.setPassword(SingletonPostgresContainer.getInstance().getPassword());
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
        "ai.turintech.modelcatalog.entity", "ai.turintech.components.jpa.search.data.entity");
    JpaVendorAdapter vendorAdapter =
        new HibernateJpaVendorAdapter(); // or any other JPA vendor adapter
    em.setJpaVendorAdapter(vendorAdapter);
    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean
  public Scheduler jdbcScheduler() {
    return Schedulers.immediate();
  }

  @Bean
  public static Properties getProperties() {
    Properties properties = new Properties();
    try {
      properties.load(Files.newInputStream(Paths.get(PROPERTIES_FILE_PATH)));
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return properties;
  }
}
