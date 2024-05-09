package ai.turintech.modelcatalog.migrationfilescreator.integration;

import org.testcontainers.containers.PostgreSQLContainer;

public class SingletonPostgresContainer {

  private static final PostgreSQLContainer<?> container;

  static {
    container =
        new PostgreSQLContainer<>("postgres:16.1")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");
    container.start();
  }

  private SingletonPostgresContainer() {}

  public static PostgreSQLContainer<?> getInstance() {
    return container;
  }
}
