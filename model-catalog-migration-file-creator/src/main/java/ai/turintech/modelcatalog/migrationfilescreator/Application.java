package ai.turintech.modelcatalog.migrationfilescreator;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.jpa.search.JpaSearchPackage;
import ai.turintech.components.jpa.search.data.entity.JpaSearchEntityPackage;
import ai.turintech.components.jpa.search.repository.JpaSearchRepositoryPackage;
import ai.turintech.modelcatalog.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import ai.turintech.modelcatalog.migrationfilescreator.service.PythonInteractionService;
import ai.turintech.modelcatalog.migrationfilescreator.service.PythonInteractionServiceImpl;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import ai.turintech.modelcatalog.service.ModelCatalogServicePackage;
import java.io.IOException;
import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@PropertySource(value = {"classpath:configuration.properties", "classpath:application.properties"})
public class Application {

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
    context.close();
  }

  @Bean
  public Scheduler jdbcScheduler() {
    return Schedulers.boundedElastic();
  }
}
