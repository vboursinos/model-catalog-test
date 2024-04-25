import database.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import database.entity.ModelCatalogEntityPackage;
import database.repository.ModelCatalogRepositoryPackage;
import database.service.ModelCatalogServicePackage;
import jakarta.persistence.EntityManagerFactory;
import java.io.IOException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import migration_files_creator.MigrationFilesCreatorPackage;
import migration_files_creator.PythonInteractionService;
import migration_files_creator.PythonInteractionServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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

/** Sets up the server and port. */
@SpringBootApplication
@ComponentScan(
    basePackageClasses = {
      ModelCatalogRepositoryPackage.class,
      MigrationFilesCreatorPackage.class,
      ModelCatalogServicePackage.class,
      ModelCatalogDtoEntityMapperPackage.class
    })
@EnableJpaRepositories(basePackageClasses = {ModelCatalogRepositoryPackage.class})
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class})
@PropertySource(value = {"/configuration.properties", "/application.properties"})
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
  private String h2Dialect;

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
    em.setPackagesToScan(ModelCatalogEntityPackage.class.getPackage().getName());
    em.setJpaVendorAdapter(vendorAdapter(Boolean.valueOf(true), h2Dialect));
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
}
