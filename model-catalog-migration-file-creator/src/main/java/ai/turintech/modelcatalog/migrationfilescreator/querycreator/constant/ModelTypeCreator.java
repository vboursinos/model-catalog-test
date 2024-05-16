package ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import ai.turintech.modelcatalog.service.ModelTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModelTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(ModelTypeCreator.class);

  private static final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Value("${json_dir_path}")
  private String jsonDirPath;

  @Autowired private ModelTypeService modelTypeService;

  public String createStaticTable() {
    Set<String> allModelTypes;
    String modelTypeSql = "";
    try {
      allModelTypes = extractAllModelTypes();
      List<ModelTypeDTO> modelTypes = modelTypeService.findAll().block();
      logger.info("Model types: " + modelTypes);
      modelTypeSql = compareModelTypes(allModelTypes, modelTypes);
    } catch (IOException e) {
      logger.error("Error while creating model types: " + e.getMessage());
    }
    return modelTypeSql;
  }

  private Set<String> extractAllModelTypes() throws IOException {
    Path dirPath = Paths.get(jsonDirPath);
    InsertStaticTables insertStaticTables = new InsertStaticTables();
    return insertStaticTables.extractUniqueValues(mapper, dirPath, ModelTypeCreator::getModelTypes);
  }

  private String compareModelTypes(Set<String> allModelTypes, List<ModelTypeDTO> modelTypes) {
    Set<String> modelTypesForDeletion = new HashSet<>();
    Set<String> foundModelTypes = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    for (ModelTypeDTO modelType : modelTypes) {
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
      sb.append(buildDeleteModelTypeSQL(modelTypesForDeletion));
    }
    allModelTypes.removeAll(foundModelTypes);
    if (allModelTypes.size() > 0) {
      logger.info("Model types for insertion: " + allModelTypes);
      sb.append(buildInsertModelTypeSQL(allModelTypes));
    }
    return sb.toString();
  }

  public static String buildInsertModelTypeSQL(Set<String> modelTypes) {
    StringBuilder sb = new StringBuilder();
    for (String modelType : modelTypes) {
      sb.append("INSERT INTO model_type(name) VALUES ('").append(modelType).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(modelType, "model_type", 0));
    }
    return sb.toString();
  }

  public static String buildDeleteModelTypeSQL(Set<String> modelTypes) {
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
