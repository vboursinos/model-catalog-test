package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import jakarta.persistence.EntityManagerFactory;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackageClasses = {ModelCatalogRepositoryPackage.class})
@ComponentScan(basePackageClasses = {ModelCatalogRepositoryPackage.class})
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class})
public class TestRepositoryConfig {

  private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean(name = "dataSource")
  public DataSource h2Database() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:sql/create_and_insert_h2.sql")
        .build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(h2Database());
    em.setPackagesToScan("ai.turintech.modelcatalog.entity");
    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
