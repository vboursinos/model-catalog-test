package ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.exceptions.ModelCatalogMigrationFileException;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.delete.DeleteDynamicTables;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.InsertDynamicTables;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.InsertDynamicTablesImpl;
import ai.turintech.modelcatalog.migrationfilescreator.utils.FileUtils;
import ai.turintech.modelcatalog.service.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
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
  private static final String PYTHON_REQ_PATH =
      "model-catalog-migration-file-creator/model-catalog-py/setup/requirements.txt";

  @Autowired private ModelService modelService;
  @Autowired private InsertDynamicTables insertDynamicTables;
  @Autowired private DeleteDynamicTables deleteDynamicTables;

  @Value("${output_sql_file_path}")
  private String outputFilePath;

  private String outputFileName;

  @Value("${liquibase_dir_path}")
  private String liquibaseDirPath;

  @Value("${default_liquibase_file_name}")
  private String defaultLiquibaseFileName;

  private static String count;

  @Transactional
  public void insertDataScripts(String constantSQL) {
    ObjectMapper mapper = new ObjectMapper();
    Path dirPath = Paths.get(JSON_DIR_PATH);
    count = FileUtils.countFiles(liquibaseDirPath);
    boolean migrationFileExists = findFileByMetamlVersion(liquibaseDirPath, getMetaMlVersion());
    outputFileName =
        Paths.get(outputFilePath, count + defaultLiquibaseFileName + getMetaMlVersion() + ".sql")
            .toString();
    if (!migrationFileExists) {
      createConstantSqlFile(constantSQL);
      processModelsInDirectory(mapper, dirPath, this::createModelSqlFile);
      File sqlFile = new File(outputFileName);
      try {
        String content = new String(Files.readAllBytes(sqlFile.toPath()));
        if (content.trim().isEmpty()) {
          logger.error("No data to insert in the sql file");
          FileUtils.deleteFile(outputFileName);
        }
      } catch (IOException e) {
        logger.error("Error when reading the file", e);
      }
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
                    throw new ModelCatalogMigrationFileException(e);
                  }
                }
              });
    } catch (IOException e) {
      logger.error("Error reading files from directory: " + e.getMessage(), e);
    }
  }

  private void createConstantSqlFile(String constantSQL) {
    FileUtils.writeToFileAppend(
        Paths.get(outputFileName).toString(), cleanupSQLScript(constantSQL));
    logger.info("constant sql file created successfully!");
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

  private String getMetaMlVersion() {
    String metamlPrefix = "metaml==";
    try (BufferedReader reader =
        new BufferedReader(new FileReader(PYTHON_REQ_PATH, Charset.defaultCharset()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith(metamlPrefix)) {
          return line.substring(metamlPrefix.length());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean findFileByMetamlVersion(String dirPath, String metamlVersion) {
    Path startPath = Paths.get(dirPath);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(startPath)) {
      for (Path path : stream) {
        if (path.getFileName().toString().contains(metamlVersion)) {
          return true;
        }
      }
    } catch (IOException e) {
      logger.error("Error reading files from directory: " + e.getMessage(), e);
    }
    return false;
  }
}
