package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.delete.DeleteDynamicTables;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.InsertDynamicTables;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.InsertDynamicTablesImpl;
import ai.turintech.modelcatalog.migrationfilescreator.exceptions.ModelCatalogMigrationFileException;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import ai.turintech.modelcatalog.migrationfilescreator.utils.FileUtils;
import ai.turintech.modelcatalog.service.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DynamicTablesQueryCreationImpl implements DynamicTablesQueryCreation {
  private static final Logger logger = LogManager.getLogger(InsertDynamicTablesImpl.class);
  private static final String JSON_DIR_PATH = "model-catalog-migration-file-creator/model_infos";

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
                    throw new ModelCatalogMigrationFileException(e);
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

    FileUtils.writeToFileAppend(Paths.get(outputFileName).toString(), sqlScriptFinal);
    logger.info(mltask + " sql file created successfully!");
  }

  private String cleanupSQLScript(String sqlScript) {
    return sqlScript.replaceAll("(?m)^[ \t]*\r?\n", "");
  }
}
