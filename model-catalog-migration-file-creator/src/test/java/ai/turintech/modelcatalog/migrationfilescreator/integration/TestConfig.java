package ai.turintech.modelcatalog.migrationfilescreator.integration;

import ai.turintech.components.jpa.search.data.entity.JpaSearchEntityPackage;
import ai.turintech.components.jpa.search.repository.JpaSearchRepositoryPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import ai.turintech.modelcatalog.migrationfilescreator.configuration.PackageScanningConfig;
import ai.turintech.modelcatalog.migrationfilescreator.configuration.PropertySourceConfig;
import ai.turintech.modelcatalog.migrationfilescreator.configuration.SchedulerConfig;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import jakarta.persistence.EntityManagerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(
    value = {
      /*
       * JpaConfig.class is not included in order to specifically address
       * configuration needed for tests.
       */
      // LiquibaseConfig.class,
      SchedulerConfig.class,
      PropertySourceConfig.class,
      PackageScanningConfig.class
    })
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = {JpaSearchRepositoryPackage.class, ModelCatalogRepositoryPackage.class})
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class, JpaSearchEntityPackage.class})
@PropertySource("classpath:configuration.properties")
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestConfig {
  private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

  private static final String PROPERTIES_FILE_PATH = "src/test/resources/configuration.properties";

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
  public static Properties getProperties() {
    Properties properties = new Properties();
    try {
      properties.load(Files.newInputStream(Paths.get(PROPERTIES_FILE_PATH)));
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return properties;
  }

  @Bean(name = "taskExecutor")
  public Executor getAsyncExecutor() {
    TaskExecutionProperties taskExecutionProperties = new TaskExecutionProperties();
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(taskExecutionProperties.getPool().getCoreSize());
    executor.setMaxPoolSize(taskExecutionProperties.getPool().getMaxSize());
    executor.setQueueCapacity(taskExecutionProperties.getPool().getQueueCapacity());
    executor.setThreadNamePrefix(taskExecutionProperties.getThreadNamePrefix());
    return executor;
  }
}
