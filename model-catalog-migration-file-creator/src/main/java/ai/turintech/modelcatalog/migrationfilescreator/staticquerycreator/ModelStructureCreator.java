package ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.service.ModelStructureTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModelStructureCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(ModelStructureCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Value("${json_dir_path}")
  private String jsonDirPath;

  @Autowired private ModelStructureTypeService modelStructureTypeService;

  public void createStaticTable(String newFileName) {
    Set<String> allModelStructures = new HashSet<>();
    try {
      allModelStructures = extractAllModelStructures();
      List<ModelStructureTypeDTO> modelStructures = modelStructureTypeService.findAll().block();
      logger.info("Model structures: " + modelStructures);
      compareModelStructures(allModelStructures, modelStructures, newFileName);
    } catch (IOException e) {
      logger.error("Error while creating model types: " + e.getMessage());
    }
  }

  private Set<String> extractAllModelStructures() throws IOException {
    Path dirPath = Paths.get(jsonDirPath);
    InsertStaticTables insertStaticTables = new InsertStaticTables();
    Set<String> allModelStructures =
        insertStaticTables.extractUniqueValues(
            mapper, dirPath, ModelStructureCreator::getModelStructureTypes);
    return allModelStructures;
  }

  private void compareModelStructures(
      Set<String> allModelStructures,
      List<ModelStructureTypeDTO> modelStructureTypes,
      String newFileName) {
    Set<String> modelStructureTypesForDeletion = new HashSet<>();
    Set<String> foundModelStructureTypes = new HashSet<>();
    for (ModelStructureTypeDTO modelStructureType : modelStructureTypes) {
      if (allModelStructures.contains(modelStructureType.getName())) {
        logger.info("Model structure type found: " + modelStructureType.getName());
        foundModelStructureTypes.add(modelStructureType.getName());
      } else {
        logger.info("Model structure type not found: " + modelStructureType.getName());
        modelStructureTypesForDeletion.add(modelStructureType.getName());
      }
    }
    if (modelStructureTypesForDeletion.size() > 0) {
      logger.info("Model structure types for deletion: " + modelStructureTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteModelStructureSQL(modelStructureTypesForDeletion), true);
    }
    allModelStructures.removeAll(foundModelStructureTypes);
    if (allModelStructures.size() > 0) {
      logger.info("Model types for insertion: " + allModelStructures);
      insertStaticTables.createSQLFile(
          newFileName, buildInsertModelStructureTypeSQL(allModelStructures), true);
    }
  }

  public static String buildInsertModelStructureTypeSQL(Set<String> modelStructureTypes) {
    StringBuilder sb = new StringBuilder();
    for (String modelStructureType : modelStructureTypes) {
      sb.append("INSERT INTO model_structure_type(name) VALUES ('")
          .append(modelStructureType)
          .append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(modelStructureType, "model_structure_type", 0));
    }
    return sb.toString();
  }

  public static String buildDeleteModelStructureSQL(Set<String> modelStructureTypes) {
    StringBuilder sb = new StringBuilder();
    for (String modelStructure : modelStructureTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(modelStructure, "model_structure_type", 2));
      sb.append("DELETE FROM model_structure_type WHERE name='")
          .append(modelStructure)
          .append("';\n");
    }
    return sb.toString();
  }

  static Set<String> getModelStructureTypes(Models models) {
    Set<String> modelStructureTypes = new HashSet<>();
    for (Model model : models.getModels()) {
      String modelStructure = model.getMetadata().getStructure();
      modelStructureTypes.add(modelStructure);
    }
    return modelStructureTypes;
  }
}
