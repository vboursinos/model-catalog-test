package ai.turintech.modelcatalog.exceptions;

/**
 * Exception in order to represent a problem in the Model Catalog. This exception is the base
 * exception for all the exceptions in the Model Catalog. It is used to represent a problem in the
 * Model Catalog.
 */
public class ModelCatalogBaseException extends Exception {

  private static final long serialVersionUID = -3560409958262459082L;

  public ModelCatalogBaseException() {
    super();
  }

  public ModelCatalogBaseException(Exception cause) {
    super(cause);
  }

  public ModelCatalogBaseException(String message) {
    super(message);
  }

  public ModelCatalogBaseException(String message, Exception cause) {
    super(message, cause);
  }
}
