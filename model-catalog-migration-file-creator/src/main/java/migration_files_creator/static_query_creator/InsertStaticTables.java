package migration_files_creator.static_query_creator;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import migration_files_creator.model.Models;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import utils.FileUtils;

@Component
public class InsertStaticTables {

  @Autowired private List<StaticTableCreator> staticTableCreators;
  private static final Logger logger = LogManager.getLogger(InsertStaticTables.class);
  private static final String JSON_DIR_PATH = "model-catalog-migration-file-creator/model_infos";

  @Value("${latest_sql_file_name}")
  private String latestSqlFileName;

  public String getJsonDirPath() {
    return JSON_DIR_PATH;
  }

  public void insertDataScripts() {
    for (StaticTableCreator staticTableCreator : staticTableCreators) {
      staticTableCreator.createStaticTable(latestSqlFileName);
    }
  }

  public void createSQLFile(String fileName, String content) {
    createSQLFile(fileName, content, false);
  }

  public void createSQLFile(String filePath, String content, boolean append) {
    if (!append) {
      FileUtils.writeToFile(filePath, content);
    } else {
      FileUtils.writeToFileAppend(filePath, content);
    }
  }

  private void processModelsInDirectory(
      ObjectMapper mapper, Path dirPath, Consumer<Models> modelProcessor) {
    try {
      Files.newDirectoryStream(dirPath)
          .forEach(
              filePath -> {
                if (Files.isRegularFile(filePath)) {
                  try {
                    modelProcessor.accept(mapper.readValue(filePath.toFile(), Models.class));
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                }
              });
    } catch (IOException e) {
      logger.error("Error reading files from directory: " + e.getMessage(), e);
    }
  }

  public Set<String> extractUniqueValues(
      ObjectMapper mapper, Path dirPath, Function<Models, Set<String>> valueExtractor) {
    Set<String> uniqueValues = new HashSet<>();
    processModelsInDirectory(
        mapper, dirPath, models -> uniqueValues.addAll(valueExtractor.apply(models)));
    return uniqueValues;
  }
}
