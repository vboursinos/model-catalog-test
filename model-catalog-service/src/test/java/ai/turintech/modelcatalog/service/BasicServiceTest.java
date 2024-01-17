package ai.turintech.modelcatalog.service;

import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(classes = TestServiceConfig.class)
public class BasicServiceTest {

  private static final PostgreSQLContainer<?> postgreSQLContainer =
      SingletonPostgresContainer.getInstance();
}
