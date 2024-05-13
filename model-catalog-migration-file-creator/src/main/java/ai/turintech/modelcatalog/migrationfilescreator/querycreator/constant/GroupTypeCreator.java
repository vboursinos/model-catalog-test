package ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.service.ModelGroupTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import java.io.File;
import java.io.IOException;
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
public class GroupTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(GroupTypeCreator.class);

  private static final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Value("${group_type_json_path}")
  private String groupTypeJsonPath;

  @Autowired private ModelGroupTypeService modelGroupTypeService;

  public String createStaticTable() {
    Set<String> allGroupTypes;
    String groupTypeSql = "";
    try {
      allGroupTypes = extractAllGroupTypes();
      List<ModelGroupTypeDTO> modelGroupTypes = modelGroupTypeService.findAll().block();
      logger.info("Model group types: " + modelGroupTypes);
      groupTypeSql = compareModelGroupTypes(allGroupTypes, modelGroupTypes);
    } catch (IOException e) {
      logger.error("Error while creating model group types: " + e.getMessage());
    }
    return groupTypeSql;
  }

  private Set<String> extractAllGroupTypes() throws IOException {
    Map<String, Map<String, List<String>>> groupTypeMap = getModelGroupTypes(groupTypeJsonPath);
    return groupTypeMap.keySet();
  }

  public Map<String, Map<String, List<String>>> getModelGroupTypes(String filePath)
      throws IOException {
    File jsonFile = new File(filePath);
    MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, String.class, Map.class);
    return mapper.readValue(jsonFile, mapType);
  }

  private String compareModelGroupTypes(
      Set<String> allModelGroupTypes, List<ModelGroupTypeDTO> modelGroupTypes) {
    Set<String> modelGroupTypesForDeletion = new HashSet<>();
    Set<String> foundModelGroupTypes = new HashSet<>();
    for (ModelGroupTypeDTO modelGroupType : modelGroupTypes) {
      if (allModelGroupTypes.contains(modelGroupType.getName())) {
        logger.info("Model Group type found: " + modelGroupType.getName());
        foundModelGroupTypes.add(modelGroupType.getName());
      } else {
        logger.info("Model Group type not found: " + modelGroupType.getName());
        modelGroupTypesForDeletion.add(modelGroupType.getName());
      }
    }
    if (modelGroupTypesForDeletion.size() > 0) {
      logger.info("Model Group types for deletion: " + modelGroupTypesForDeletion);
      return buildDeleteGroupTypeSQL(modelGroupTypesForDeletion);
    }
    allModelGroupTypes.removeAll(foundModelGroupTypes);
    if (allModelGroupTypes.size() > 0) {
      logger.info("Model Group types for insertion: " + allModelGroupTypes);
      return buildInsertGroupTypeSQL(allModelGroupTypes);
    }
    return "\n";
  }

  public static String buildInsertGroupTypeSQL(Set<String> groups) {
    StringBuilder sb = new StringBuilder();
    for (String group : groups) {
      sb.append("INSERT INTO model_group_type(name) VALUES ('").append(group).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(group, "model_group_type", 0));
    }
    return sb.toString();
  }

  public static String buildDeleteGroupTypeSQL(Set<String> groups) {
    StringBuilder sb = new StringBuilder();
    for (String group : groups) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(group, "model_group_type", 2));
      sb.append("DELETE FROM model_group_type WHERE name='").append(group).append("';\n");
    }
    return sb.toString();
  }
}
