package ai.turintech.modelcatalog.rest.exceptions;

/**
 * Exception in order to represent a problem in db when we populate the tables of the db.
 *
 * @author vasilis
 */
public class FindOneException extends ModelCatalogBaseException {

  private static final long serialVersionUID = 1023277841825618529L;

  public FindOneException() {
    super();
  }

  public FindOneException(Exception cause) {
    super(cause);
  }

  public FindOneException(String message) {
    super(message);
  }

  public FindOneException(String message, Exception cause) {
    super(message, cause);
  }
}
