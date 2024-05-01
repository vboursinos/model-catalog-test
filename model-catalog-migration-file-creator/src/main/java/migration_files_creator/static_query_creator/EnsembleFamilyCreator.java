package migration_files_creator.static_query_creator;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.service.ModelEnsembleTypeService;
import ai.turintech.modelcatalog.service.ModelFamilyTypeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;
import migration_files_creator.model.EnsembleFamily;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnsembleFamilyCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(EnsembleFamilyCreator.class);

  private static final String ENSEMBLE_FAMILY_JSON_FILE_PATH =
      "model-catalog-migration-file-creator/static/ensemble-family.json";

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private ModelEnsembleTypeService modelEnsembleTypeService;

  @Autowired private ModelFamilyTypeService modelFamilyTypeService;

  public void createStaticTable(String newFileName) {
    Map<String, Set<String>> allFamilyEnsembleTypes = new HashMap<>();
    try {
      allFamilyEnsembleTypes = extractAllFamilyEnsembleTypes();
      List<ModelEnsembleTypeDTO> modelEnsembleTypes = modelEnsembleTypeService.findAll().block();
      List<ModelFamilyTypeDTO> modelFamilyTypes = modelFamilyTypeService.findAll().block();
      logger.info("Model Ensemble Types: " + modelEnsembleTypes);
      logger.info("Model Family Types: " + modelFamilyTypes);
      compareModelEnsembleTypes(
          allFamilyEnsembleTypes.get("ensemble"), modelEnsembleTypes, newFileName);
      compareModelFamilyTypes(allFamilyEnsembleTypes.get("family"), modelFamilyTypes, newFileName);
    } catch (IOException e) {
      logger.error("Error extracting family and ensemble types: " + e.getMessage(), e);
    }
  }

  private Map<String, Set<String>> extractAllFamilyEnsembleTypes() throws IOException {
    Map<String, Set<String>> allFamilyEnsembleTypes = getFamilyEnsembleTypes();
    return allFamilyEnsembleTypes;
  }

  private void compareModelEnsembleTypes(
      Set<String> allEnsembleTypes,
      List<ModelEnsembleTypeDTO> modelEnsembleTypes,
      String newFileName) {
    Set<String> ensembleTypesForDeletion = new HashSet<>();
    Set<String> foundEnsembleTypes = new HashSet<>();
    for (ModelEnsembleTypeDTO modelEnsembleType : modelEnsembleTypes) {
      if (allEnsembleTypes.contains(modelEnsembleType.getName())) {
        logger.info("Model ensemble type found: " + modelEnsembleType.getName());
        foundEnsembleTypes.add(modelEnsembleType.getName());
      } else {
        logger.info("Model ensemble type not found: " + modelEnsembleType.getName());
        ensembleTypesForDeletion.add(modelEnsembleType.getName());
      }
    }
    if (ensembleTypesForDeletion.size() > 0) {
      logger.info("Model ensemble types for deletion: " + ensembleTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteEnsembleTypeSQL(ensembleTypesForDeletion), true);
    }
    allEnsembleTypes.removeAll(foundEnsembleTypes);
    if (allEnsembleTypes.size() > 0) {
      logger.info("Model ensemble for insertion: " + allEnsembleTypes);
      insertStaticTables.createSQLFile(
          newFileName, buildInsertEnsembleTypeSQL(allEnsembleTypes), true);
    }
  }

  private void compareModelFamilyTypes(
      Set<String> allFamilyTypes, List<ModelFamilyTypeDTO> modelFamilyTypes, String newFileName) {
    Set<String> familyTypesForDeletion = new HashSet<>();
    Set<String> foundFamilyTypes = new HashSet<>();
    for (ModelFamilyTypeDTO modelFamilyType : modelFamilyTypes) {
      if (allFamilyTypes.contains(modelFamilyType.getName())) {
        logger.info("Model family type found: " + modelFamilyType.getName());
        foundFamilyTypes.add(modelFamilyType.getName());
      } else {
        logger.info("Model family type not found: " + modelFamilyType.getName());
        familyTypesForDeletion.add(modelFamilyType.getName());
      }
    }
    if (familyTypesForDeletion.size() > 0) {
      logger.info("Model family types for deletion: " + familyTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteFamilyTypeSQL(familyTypesForDeletion), true);
    }
    allFamilyTypes.removeAll(foundFamilyTypes);
    if (allFamilyTypes.size() > 0) {
      logger.info("Model family for insertion: " + allFamilyTypes);
      insertStaticTables.createSQLFile(newFileName, buildInsertFamilyTypeSQL(allFamilyTypes), true);
    }
  }

  static Map<String, Set<String>> getFamilyEnsembleTypes() {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<String> familyTypes = new HashSet<>();
    Set<String> ensembleTypes = new HashSet<>();
    Map<String, Set<String>> familyEnsembleTypes = new HashMap<>();
    try {
      List<EnsembleFamily> modelList =
          objectMapper.readValue(
              new File(ENSEMBLE_FAMILY_JSON_FILE_PATH), new TypeReference<>() {});

      for (EnsembleFamily model : modelList) {
        ensembleTypes.add(model.getEnsembleType());
        familyTypes.add(model.getFamily());
      }
    } catch (IOException e) {
      logger.error("Error reading ensemble-family.json file: " + e.getMessage(), e);
    }
    familyEnsembleTypes.put("ensemble", new HashSet<>(ensembleTypes));
    familyEnsembleTypes.put("family", new HashSet<>(familyTypes));
    return familyEnsembleTypes;
  }

  static String buildInsertFamilyTypeSQL(Set<String> familyTypes) {
    StringBuilder sb = new StringBuilder();
    for (String familyType : familyTypes) {
      sb.append("INSERT INTO model_family_type(name) VALUES ('").append(familyType).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(familyType, "model_family_type", 0));
    }
    return sb.toString();
  }

  static String buildInsertEnsembleTypeSQL(Set<String> ensembleTypes) {
    StringBuilder sb = new StringBuilder();
    for (String ensembleType : ensembleTypes) {
      sb.append("INSERT INTO model_ensemble_type(name) VALUES ('")
          .append(ensembleType)
          .append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(ensembleType, "model_ensemble_type", 0));
    }
    return sb.toString();
  }

  static String buildDeleteEnsembleTypeSQL(Set<String> ensembleTypes) {
    StringBuilder sb = new StringBuilder();
    for (String ensembleTYpe : ensembleTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(ensembleTYpe, "model_family_type", 2));
      sb.append("DELETE FROM model_ensemble_type WHERE name='").append(ensembleTYpe).append("';\n");
    }
    return sb.toString();
  }

  static String buildDeleteFamilyTypeSQL(Set<String> familyTypes) {
    StringBuilder sb = new StringBuilder();
    for (String familyType : familyTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(familyType, "model_family_type", 2));
      sb.append("DELETE FROM model_family_type WHERE name='").append(familyType).append("';\n");
    }
    return sb.toString();
  }
}
