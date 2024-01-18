package ai.turintech.modelcatalog.rest;

import ai.turintech.components.architecture.ArchitectureServicePackage;
import ai.turintech.components.architecture.facade.impl.ArchitectureFacadePackage;
import ai.turintech.components.architecture.rest.ArchitectureRestPackage;
import ai.turintech.components.jpa.search.JpaSearchPackage;
import ai.turintech.components.jpa.search.data.entity.JpaSearchEntityPackage;
import ai.turintech.components.jpa.search.repository.JpaSearchRepositoryPackage;
import ai.turintech.modelcatalog.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import ai.turintech.modelcatalog.facade.ModelPackageFacadePackage;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import ai.turintech.modelcatalog.service.ModelCatalogServicePackage;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
@EnableJpaRepositories(
    basePackageClasses = {ModelCatalogRepositoryPackage.class, JpaSearchRepositoryPackage.class})
@ComponentScan(
    basePackageClasses = {
      ModelCatalogRepositoryPackage.class,
      ModelCatalogServicePackage.class,
      ModelPackageFacadePackage.class,
      ModelCatalogDtoEntityMapperPackage.class,
      JpaSearchPackage.class,
      ArchitectureServicePackage.class,
      ArchitectureFacadePackage.class,
      ArchitectureRestPackage.class
    })
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class, JpaSearchEntityPackage.class})
public class TestRestConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
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

//  @Bean
//  public WebTestClient webTestClient() {
//    // configure and return the WebTestClient instance
//    return WebTestClient.bindToServer().baseUrl("http://localhost").build();
//  }
//
//  @Bean
//  public WebClient.Builder webClientBuilder() {
//    return WebClient.builder();
//  }
//
//  @Bean
//  public ReactiveWebServerFactory reactiveWebServerFactory() {
//    return new NettyReactiveWebServerFactory(); // Use the appropriate server factory
//  }
//
//  @Bean
//  public HttpHandler httpHandler(WebHandler webHandler) {
//    return WebHttpHandlerBuilder.webHandler(webHandler).build();
//  }
}
