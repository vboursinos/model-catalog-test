package migration_files_creator.init;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelMapper;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.repository.ModelRepository;
import ai.turintech.modelcatalog.service.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.FileUtils;

@Component
public class DynamicTablesQueryCreationImpl implements DynamicTablesQueryCreation {
  private static final Logger logger = LogManager.getLogger(InsertDynamicTables.class);
  private static final String JSON_DIR_PATH = "model-catalog-migration-file-creator/model_infos";
  private static final String SQL_DIR_PATH = "model-catalog-migration-file-creator/sql_scripts";

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private ModelService modelService;

  @Autowired private ModelRepository modelRepository;

  @Autowired private InsertDynamicTables insertDynamicTables;

  @Autowired private DeleteDynamicTables deleteDynamicTables;

  @Autowired private ModelMapper modelMapper;

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

  @Transactional
  private void createModelSqlFile(Models models) {
    String mltask = models.getModels().get(0).getMlTask();
    List<Model> dbModels = modelRepository.findAll();
    List<ModelDTO> modelsDTO = dbModels.stream().map(modelMapper::to).toList();
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
