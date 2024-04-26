import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.jpa.search.JpaSearchPackage;
import ai.turintech.components.jpa.search.data.entity.JpaSearchEntityPackage;
import ai.turintech.components.jpa.search.repository.JpaSearchRepositoryPackage;
import ai.turintech.modelcatalog.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import ai.turintech.modelcatalog.service.ModelCatalogServicePackage;
import jakarta.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.naming.NamingException;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import migration_files_creator.MigrationFilesCreatorPackage;
import migration_files_creator.PythonInteractionService;
import migration_files_creator.PythonInteractionServiceImpl;
import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication(exclude = {SpringDocDataRestConfiguration.class})
@ComponentScan(
    basePackageClasses = {
      ModelCatalogRepositoryPackage.class,
      MigrationFilesCreatorPackage.class,
      ModelCatalogDtoEntityMapperPackage.class,
      ModelCatalogServicePackage.class,
      JpaSearchPackage.class,
      ReactiveAbstractUUIDIdentityCrudCallableImpl.class
    })
@EnableJpaRepositories(
    basePackageClasses = {JpaSearchRepositoryPackage.class, ModelCatalogRepositoryPackage.class})
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class, JpaSearchEntityPackage.class})
@PropertySource(value = {"/configuration.properties", "/application.properties"})
@EnableConfigurationProperties({LiquibaseProperties.class})
public class Application {

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.username}")
  private String datasourceUsername;

  @Value("${spring.datasource.password}")
  private String datasourcePassword;

  @Value("${spring.datasource.driver-class-name}")
  private String datasourceDriverClassName;

  @Value("${spring.jpa.database-platform}")
  private String postgresDialect;

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    SpringApplication app = new SpringApplication(Application.class);
    ConfigurableApplicationContext context = app.run(args);
    PythonInteractionService pythonInteraction =
        context.getBean(PythonInteractionServiceImpl.class);
    pythonInteraction.main();
    context.close(); // Close the application context when done
  }

  @Bean(name = "dataSource")
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(datasourceDriverClassName);
    dataSource.setUrl(datasourceUrl);
    dataSource.setUsername(datasourceUsername);
    dataSource.setPassword(datasourcePassword);
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan(
        ModelCatalogEntityPackage.class.getPackage().getName(),
        JpaSearchEntityPackage.class.getPackage().getName());
    em.setJpaVendorAdapter(vendorAdapter(Boolean.valueOf(true), postgresDialect));
    return em;
  }

  @Primary
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  public JpaVendorAdapter vendorAdapter(Boolean showSql, String dialect) {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setShowSql(showSql);
    vendorAdapter.setDatabasePlatform(dialect);
    return vendorAdapter;
  }

  @Bean
  public Scheduler jdbcScheduler() {
    return Schedulers.boundedElastic();
  }

  @Bean
  public SpringLiquibase liquibase(
      @Qualifier("taskExecutor") Executor executor, LiquibaseProperties liquibaseProperties) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(createLiquibaseDataSource(liquibaseProperties));
    liquibase.setChangeLog("classpath:liquibase/master.xml");
    liquibase.setContexts(liquibaseProperties.getContexts());
    liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
    liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
    liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
    liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
    liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
    liquibase.setDropFirst(liquibaseProperties.isDropFirst());
    liquibase.setLabelFilter(liquibaseProperties.getLabelFilter());
    liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
    liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
    liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());

    liquibase.setShouldRun(liquibaseProperties.isEnabled());

    return liquibase;
  }

  private static DataSource createLiquibaseDataSource(LiquibaseProperties liquibaseProperties) {
    return DataSourceBuilder.create()
        .url(liquibaseProperties.getUrl())
        .username(liquibaseProperties.getUser())
        .password(liquibaseProperties.getPassword())
        .build();
  }
}
