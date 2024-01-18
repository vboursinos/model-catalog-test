package ai.turintech.modelcatalog.rest;

import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(classes = TestRestConfig.class)
public class BasicRestTest {

  private static final PostgreSQLContainer<?> postgreSQLContainer =
      SingletonPostgresContainer.getInstance();
}
