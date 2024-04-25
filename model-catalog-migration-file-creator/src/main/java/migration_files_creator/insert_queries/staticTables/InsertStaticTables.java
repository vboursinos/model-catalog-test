package migration_files_creator.insert_queries.staticTables;

import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.PropertiesConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import migration_files_creator.model.Models;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.FileUtils;

@Component
public class InsertStaticTables {

  @Autowired private List<StaticTableCreator> staticTableCreators;
  private static final Logger logger = LogManager.getLogger(InsertStaticTables.class);
  private static final String JSON_DIR_PATH = "model_infos";
  private static final String SQL_DIR_PATH = "sql_scripts";

  public String getJsonDirPath() {
    return JSON_DIR_PATH;
  }

  public String getSqlDirPath() {
    return SQL_DIR_PATH;
  }

  public String getFilename() {
    Properties properties = PropertiesConfig.getProperties();
    String latestSqlFileName = properties.getProperty("latest_sql_file_name");
    return latestSqlFileName;
  }

  public void insertDataScripts() {
    for (StaticTableCreator staticTableCreator : staticTableCreators) {
      staticTableCreator.createStaticTable();
    }
  }

  public void createSQLFile(String fileName, String content) {
    createSQLFile(fileName, content, false);
  }

  public void createSQLFile(String fileName, String content, boolean append) {
    Path sqlDirPath = Paths.get(SQL_DIR_PATH, fileName);
    if (!append) {
      FileUtils.writeToFile(sqlDirPath.toString(), content);
    } else {
      FileUtils.writeToFileAppend(sqlDirPath.toString(), content);
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
