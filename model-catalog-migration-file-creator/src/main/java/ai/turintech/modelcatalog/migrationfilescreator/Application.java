package ai.turintech.modelcatalog.migrationfilescreator;

import ai.turintech.modelcatalog.migrationfilescreator.configuration.JpaConfiguration;
import ai.turintech.modelcatalog.migrationfilescreator.configuration.LiquibaseConfig;
import ai.turintech.modelcatalog.migrationfilescreator.configuration.PackageScanningConfig;
import ai.turintech.modelcatalog.migrationfilescreator.configuration.PropertySourceConfig;
import ai.turintech.modelcatalog.migrationfilescreator.configuration.SchedulerConfig;
import ai.turintech.modelcatalog.migrationfilescreator.service.PythonInteractionService;
import ai.turintech.modelcatalog.migrationfilescreator.service.PythonInteractionServiceImpl;
import java.io.IOException;
import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {SpringDocDataRestConfiguration.class})
@Import(
    value = {
      JpaConfiguration.class,
      SchedulerConfig.class,
      LiquibaseConfig.class,
      PackageScanningConfig.class,
      PropertySourceConfig.class
    })
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
}
