package integration;

import java.io.File;
import org.junit.jupiter.api.AfterAll;

public class BaseBuildQueryTest {

  protected static final String FILE_NAME = "src/test/java/integration/migration_file.sql";

  @AfterAll
  public static void cleanUp() {
    File file = new File(FILE_NAME);
    if (file.exists()) {
      file.delete();
    }
  }
}
