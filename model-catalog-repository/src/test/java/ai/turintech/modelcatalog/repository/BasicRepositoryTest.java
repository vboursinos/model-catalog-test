package ai.turintech.modelcatalog.repository;

import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(classes = TestConfig.class)
public class BasicRepositoryTest {

  private static final PostgreSQLContainer<?> postgreSQLContainer =
      SingletonPostgresContainer.getInstance();
}
