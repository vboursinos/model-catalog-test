package ai.turintech.modelcatalog.rest.config;

import ai.turintech.components.jpa.search.data.entity.IgvSearchEntityPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import jakarta.persistence.EntityManagerFactory;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
// @EnableJpaRepositories(basePackages = "ai.turintech.modelcatalog.repository")
public class JpaConfiguration {

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.username}")
  private String datasourceUsername;

  @Value("${spring.datasource.password}")
  private String datasourcePassword;

  @Value("${spring.datasource.driver-class-name}")
  private String datasourceDriverClassName;

  @Value("${spring.jpa.properties.hibernate.dialect}")
  private String postgresDialect;

  @Bean(name = "dataSource")
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(datasourceDriverClassName);
    dataSource.setUrl(datasourceUrl);
    dataSource.setUsername(datasourceUsername);
    dataSource.setPassword(datasourcePassword);
    return dataSource;
  }

  //    @Bean(name = "entityManagerFactory")
  //    public LocalSessionFactoryBean sessionFactory() {
  //        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
  //
  // sessionFactory.setPackagesToScan(ModelCatalogEntityPackage.class.getPackage().getName(),IgvSearchEntityPackage.class.getPackage().getName());
  //        sessionFactory.setDataSource(dataSource());
  //
  //        return sessionFactory;
  //    }

  //    @Primary
  //    @Bean
  //    public PlatformTransactionManager transactionManager() {
  //        JpaTransactionManager transactionManager = new JpaTransactionManager();
  //        transactionManager.setEntityManagerFactory(sessionFactory().getObject());
  //        return transactionManager;
  //    }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan(
        ModelCatalogEntityPackage.class.getPackage().getName(),
        IgvSearchEntityPackage.class.getPackage().getName());
    em.setJpaVendorAdapter(vendorAdapter(Boolean.valueOf(true), postgresDialect));
    //        em.setJpaProperties(additionalProperties(JPA_PROPERTIES));
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

  //    private Properties additionalProperties(String hbm2dllAuto) {
  //        Properties properties = new Properties();
  //        properties.setProperty(Environment.HBM2DDL_AUTO, hbm2dllAuto);
  //        return properties;
  //    }

}
