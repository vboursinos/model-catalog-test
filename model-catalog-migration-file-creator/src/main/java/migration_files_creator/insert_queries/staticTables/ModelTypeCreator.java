package migration_files_creator.insert_queries.staticTables;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.entity.ModelType;
import database.service.interfaces.ModelTypeService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import migration_files_creator.model.Model;
import migration_files_creator.model.Models;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(ModelTypeCreator.class);

  private static final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private ModelTypeService modelTypeService;

  public void createStaticTable() {
    Set<String> allModelTypes;
    try {
      allModelTypes = extractAllModelTypes();
      List<ModelType> modelTypes = modelTypeService.findAll();
      logger.info("Model types: " + modelTypes);
      compareModelTypes(allModelTypes, modelTypes);
    } catch (IOException e) {
      logger.error("Error while creating model types: " + e.getMessage());
    }
  }

  private Set<String> extractAllModelTypes() throws IOException {
    Path dirPath = Paths.get(insertStaticTables.getJsonDirPath());
    InsertStaticTables insertStaticTables = new InsertStaticTables();
    return insertStaticTables.extractUniqueValues(mapper, dirPath, ModelTypeCreator::getModelTypes);
  }

  private void compareModelTypes(Set<String> allModelTypes, List<ModelType> modelTypes) {
    String newFileName = insertStaticTables.getFilename();
    Set<String> modelTypesForDeletion = new HashSet<>();
    Set<String> foundModelTypes = new HashSet<>();
    for (ModelType modelType : modelTypes) {
      if (allModelTypes.contains(modelType.getName())) {
        logger.info("Model type found: " + modelType.getName());
        foundModelTypes.add(modelType.getName());
      } else {
        logger.info("Model type not found: " + modelType.getName());
        modelTypesForDeletion.add(modelType.getName());
      }
    }
    if (modelTypesForDeletion.size() > 0) {
      logger.info("Model types for deletion: " + modelTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteModelTypeSQL(modelTypesForDeletion), true);
    }
    allModelTypes.removeAll(foundModelTypes);
    if (allModelTypes.size() > 0) {
      logger.info("Model types for insertion: " + allModelTypes);
      insertStaticTables.createSQLFile(newFileName, buildInsertModelTypeSQL(allModelTypes), true);
    }
  }

  static String buildInsertModelTypeSQL(Set<String> modelTypes) {
    StringBuilder sb = new StringBuilder();
    for (String modelType : modelTypes) {
      sb.append("INSERT INTO model_type(name) VALUES ('").append(modelType).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(modelType, "model_type", 0));
    }
    return sb.toString();
  }

  static String buildDeleteModelTypeSQL(Set<String> modelTypes) {
    StringBuilder sb = new StringBuilder();
    for (String modelType : modelTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(modelType, "model_type", 2));
      sb.append("DELETE FROM model_type WHERE name='").append(modelType).append("';\n");
    }
    return sb.toString();
  }

  static Set<String> getModelTypes(Models models) {
    Set<String> modelTypes = new HashSet<>();
    for (Model model : models.getModels()) {
      List<String> modelType = model.getMetadata().getModelType();
      if (modelType != null && !modelType.isEmpty()) {
        modelTypes.addAll(modelType);
      }
    }
    return modelTypes;
  }
}
