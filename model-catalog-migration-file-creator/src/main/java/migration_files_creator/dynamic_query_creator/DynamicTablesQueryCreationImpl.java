package migration_files_creator.dynamic_query_creator;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.service.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import migration_files_creator.dynamic_query_creator.delete_queries.DeleteDynamicTables;
import migration_files_creator.dynamic_query_creator.insert_queries.InsertDynamicTables;
import migration_files_creator.dynamic_query_creator.insert_queries.InsertDynamicTablesImpl;
import migration_files_creator.model.Models;
import migration_files_creator.static_query_creator.InsertStaticTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.FileUtils;

@Component
public class DynamicTablesQueryCreationImpl implements DynamicTablesQueryCreation {
  private static final Logger logger = LogManager.getLogger(InsertDynamicTablesImpl.class);
  private static final String JSON_DIR_PATH = "model-catalog-migration-file-creator/model_infos";
  private static final String SQL_DIR_PATH = "model-catalog-migration-file-creator/sql_scripts";

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private ModelService modelService;
  @Autowired private InsertDynamicTables insertDynamicTables;
  @Autowired private DeleteDynamicTables deleteDynamicTables;

  @Value("${latest_sql_file_name}")
  private String outputFileName;

  @Transactional
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

  private void createModelSqlFile(Models models) {
    String mltask = models.getModels().get(0).getMlTask();
    List<ModelDTO> modelsDTO = modelService.findAll().collectList().block();
    String sqlScriptDelete = deleteDynamicTables.buildDeleteSQL(mltask, models, modelsDTO);
    String sqlScriptInsert = insertDynamicTables.buildInsertSQL(models, modelsDTO);
    String sqlScriptFinal = cleanupSQLScript(sqlScriptDelete + "\n" + sqlScriptInsert);

    FileUtils.writeToFileAppend(Paths.get(SQL_DIR_PATH, outputFileName).toString(), sqlScriptFinal);
    logger.info(mltask + " sql file created successfully!");
  }

  private String cleanupSQLScript(String sqlScript) {
    return sqlScript.replaceAll("(?m)^[ \t]*\r?\n", "");
  }
}
