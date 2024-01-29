package ai.turintech.modelcatalog.rest;

import org.testcontainers.containers.PostgreSQLContainer;

public class SingletonPostgresContainer {

  private static final PostgreSQLContainer<?> container;

  static {
    container =
        new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("sql/create_and_insert.sql");
    container.start();
  }

  private SingletonPostgresContainer() {}

  public static PostgreSQLContainer<?> getInstance() {
    return container;
  }
}
