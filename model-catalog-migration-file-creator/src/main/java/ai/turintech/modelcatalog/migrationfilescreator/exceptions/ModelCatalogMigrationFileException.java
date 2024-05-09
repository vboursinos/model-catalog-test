package ai.turintech.modelcatalog.migrationfilescreator.exceptions;

public class ModelCatalogMigrationFileException extends RuntimeException {

  private static final long serialVersionUID = -3560409958262459082L;

  public ModelCatalogMigrationFileException() {
    super();
  }

  public ModelCatalogMigrationFileException(Exception cause) {
    super(cause);
  }

  public ModelCatalogMigrationFileException(String message) {
    super(message);
  }

  public ModelCatalogMigrationFileException(String message, Exception cause) {
    super(message, cause);
  }
}
