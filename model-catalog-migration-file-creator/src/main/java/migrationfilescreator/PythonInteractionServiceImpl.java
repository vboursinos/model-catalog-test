package migrationfilescreator;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PythonInteractionServiceImpl implements PythonInteractionService {
  private static final Logger logger = LogManager.getLogger(PythonInteractionServiceImpl.class);

  @Autowired private CreateSqlScriptService createSqlScript;

  public void main() throws IOException, InterruptedException {
    String script = "model-catalog-migration-file-creator/model-catalog-py/src/main.py";
    ProcessBuilder pb;
    Process p;
    pb =
        new ProcessBuilder("bash", "-c", String.format("python3 %s", script))
            .redirectErrorStream(true)
            .inheritIO();
    pb.directory(Paths.get(".").toFile());
    logger.info("Python script is running");
    p = pb.start();
    if (p.waitFor() != 0) {
      System.err.println("An error ocurred executing the conversion script.");
      throw new IOException(p.getErrorStream().toString());
    }
    logger.info("Script executed successfully");
    createSqlScript.createFiles();
  }
}
