package migration_files_creator.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.dto.ModelDTO;
import database.dtoentitymapper.ModelMapper;
import database.service.interfaces.ModelService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import migration_files_creator.delete_queries.DeleteDynamicTables;
import migration_files_creator.insert_queries.dynamicTables.InsertDynamicTables;
import migration_files_creator.insert_queries.staticTables.InsertStaticTables;
import migration_files_creator.model.Models;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.FileUtils;

@Component
public class DynamicTablesQueryCreationImpl implements DynamicTablesQueryCreation {
  private static final Logger logger = LogManager.getLogger(InsertDynamicTables.class);
  private static final String JSON_DIR_PATH = "model_infos";
  private static final String SQL_DIR_PATH = "sql_scripts";

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private ModelService modelService;

  @Autowired private InsertDynamicTables insertDynamicTables;

  @Autowired private DeleteDynamicTables deleteDynamicTables;

  @Autowired private ModelMapper modelMapper;

  public void insertDataScripts() {
    ObjectMapper mapper = new ObjectMapper();
    Path dirPath = Paths.get(JSON_DIR_PATH);
    processModelsInDirectory(mapper, dirPath, this::createModelSqlFile);
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

  @Transactional
  private void createModelSqlFile(Models models) {
    String mltask = models.getModels().get(0).getMlTask();
    String outputFileName = determineOutputFileName();

    List<ModelDTO> modelsDTO = modelService.findAll().stream().map(modelMapper::to).toList();
    String sqlScriptDelete = deleteDynamicTables.buildDeleteSQL(mltask, models, modelsDTO);
    String sqlScriptInsert = insertDynamicTables.buildInsertSQL(models, modelsDTO);
    String sqlScriptFinal = cleanupSQLScript(sqlScriptDelete + "\n" + sqlScriptInsert);

    FileUtils.writeToFileAppend(Paths.get(SQL_DIR_PATH, outputFileName).toString(), sqlScriptFinal);
    logger.info(mltask + " sql file created successfully!");
  }

  private String determineOutputFileName() {
    return insertStaticTables.getFilename();
  }

  private String cleanupSQLScript(String sqlScript) {
    return sqlScript.replaceAll("(?m)^[ \t]*\r?\n", "");
  }
}
