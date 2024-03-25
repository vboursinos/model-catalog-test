package ai.turintech.modelcatalog.facade;

import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(classes = TestFacadeConfig.class)
public class BasicFacadeTest {

  private static final PostgreSQLContainer<?> postgreSQLContainer =
      SingletonPostgresContainer.getInstance();
}
