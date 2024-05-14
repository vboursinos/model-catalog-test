package ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.EnsembleFamily;
import ai.turintech.modelcatalog.service.ModelEnsembleTypeService;
import ai.turintech.modelcatalog.service.ModelFamilyTypeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnsembleFamilyCreator extends TableCreatorHelper implements StaticTableCreator {
  private static final Logger logger = LogManager.getLogger(EnsembleFamilyCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private ModelEnsembleTypeService modelEnsembleTypeService;

  @Autowired private ModelFamilyTypeService modelFamilyTypeService;

  @Value("${ensemble_family_json_path}")
  private String ensembleFamilyJsonPath;

  public String createStaticTable() {
    Map<String, Set<String>> allFamilyEnsembleTypes = new HashMap<>();
    String ensembleTypeSql = "";
    String familyTypeSql = "";
    try {
      allFamilyEnsembleTypes = extractAllFamilyEnsembleTypes();
      List<ModelEnsembleTypeDTO> modelEnsembleTypes = modelEnsembleTypeService.findAll().block();
      List<ModelFamilyTypeDTO> modelFamilyTypes = modelFamilyTypeService.findAll().block();
      logger.info("Model Ensemble Types: " + modelEnsembleTypes);
      logger.info("Model Family Types: " + modelFamilyTypes);
      ensembleTypeSql =
          compareModelEnsembleTypes(allFamilyEnsembleTypes.get("ensemble"), modelEnsembleTypes);
      familyTypeSql =
          compareModelFamilyTypes(allFamilyEnsembleTypes.get("family"), modelFamilyTypes);
    } catch (IOException e) {
      logger.error("Error extracting family and ensemble types: " + e.getMessage(), e);
    }
    return ensembleTypeSql.concat(familyTypeSql);
  }

  private Map<String, Set<String>> extractAllFamilyEnsembleTypes() throws IOException {
    Map<String, Set<String>> allFamilyEnsembleTypes = getFamilyEnsembleTypes();
    return allFamilyEnsembleTypes;
  }

  private String compareModelEnsembleTypes(
      Set<String> allEnsembleTypes, List<ModelEnsembleTypeDTO> modelEnsembleTypes) {
    Set<String> ensembleTypesForDeletion = new HashSet<>();
    Set<String> foundEnsembleTypes = new HashSet<>();
    StringBuilder sb = new StringBuilder();
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
      sb.append(buildDeleteEnsembleTypeSQL(ensembleTypesForDeletion));
    }
    allEnsembleTypes.removeAll(foundEnsembleTypes);
    if (allEnsembleTypes.size() > 0) {
      logger.info("Model ensemble for insertion: " + allEnsembleTypes);
      sb.append(buildInsertEnsembleTypeSQL(allEnsembleTypes));
    }
    return sb.toString();
  }

  private String compareModelFamilyTypes(
      Set<String> allFamilyTypes, List<ModelFamilyTypeDTO> modelFamilyTypes) {
    Set<String> familyTypesForDeletion = new HashSet<>();
    Set<String> foundFamilyTypes = new HashSet<>();
    StringBuilder sb = new StringBuilder();
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
      sb.append(buildDeleteFamilyTypeSQL(familyTypesForDeletion));
    }
    allFamilyTypes.removeAll(foundFamilyTypes);
    if (allFamilyTypes.size() > 0) {
      logger.info("Model family for insertion: " + allFamilyTypes);
      sb.append(buildInsertFamilyTypeSQL(allFamilyTypes));
    }
    return sb.toString();
  }

  public Map<String, Set<String>> getFamilyEnsembleTypes() {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<String> familyTypes = new HashSet<>();
    Set<String> ensembleTypes = new HashSet<>();
    Map<String, Set<String>> familyEnsembleTypes = new HashMap<>();
    try {
      List<EnsembleFamily> modelList =
          objectMapper.readValue(new File(ensembleFamilyJsonPath), new TypeReference<>() {});

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

  public static String buildInsertFamilyTypeSQL(Set<String> familyTypes) {
    StringBuilder sb = new StringBuilder();
    for (String familyType : familyTypes) {
      sb.append("INSERT INTO model_family_type(name) VALUES ('").append(familyType).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(familyType, "model_family_type", 0));
    }
    return sb.toString();
  }

  public static String buildInsertEnsembleTypeSQL(Set<String> ensembleTypes) {
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

  public static String buildDeleteEnsembleTypeSQL(Set<String> ensembleTypes) {
    StringBuilder sb = new StringBuilder();
    for (String ensembleTYpe : ensembleTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(ensembleTYpe, "model_ensemble_type", 2));
      sb.append("DELETE FROM model_ensemble_type WHERE name='").append(ensembleTYpe).append("';\n");
    }
    return sb.toString();
  }

  public static String buildDeleteFamilyTypeSQL(Set<String> familyTypes) {
    StringBuilder sb = new StringBuilder();
    for (String familyType : familyTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(familyType, "model_family_type", 2));
      sb.append("DELETE FROM model_family_type WHERE name='").append(familyType).append("';\n");
    }
    return sb.toString();
  }
}
