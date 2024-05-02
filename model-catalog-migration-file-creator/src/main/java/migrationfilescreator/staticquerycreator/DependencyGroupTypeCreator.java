package migrationfilescreator.staticquerycreator;

import ai.turintech.modelcatalog.dto.DependencyGroupTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.DependencyGroupTypeMapper;
import ai.turintech.modelcatalog.entity.DependencyType;
import ai.turintech.modelcatalog.service.DependencyGroupTypeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import migrationfilescreator.model.Model;
import migrationfilescreator.model.Models;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DependencyGroupTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(DependencyGroupTypeCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private DependencyGroupTypeService dependencyGroupTypeService;

  @Autowired private DependencyGroupTypeMapper dependencyGroupTypeMapper;

  @Value("${dependency_types_json_path}")
  private String dependencyTypesJsonPath;

  @Value("${json_dir_path}")
  private String jsonDirPath;

  public void createStaticTable(String latestFileName) {
    Set<String> allDependencyGroupTypes;
    Map<String, Set<String>> allDependencyTypes;
    try {
      allDependencyGroupTypes = extractAllDependencyGroupTypes();
      allDependencyTypes = getDependencies();
      List<DependencyGroupTypeDTO> dependencyGroupTypes =
          dependencyGroupTypeService.findAll().block();
      List<DependencyType> dependencyTypes = getDependencyTypes(dependencyGroupTypes);
      logger.info("Dependency group types: " + dependencyGroupTypes);
      compareDependencyGroupTypes(allDependencyGroupTypes, dependencyGroupTypes, latestFileName);
      compareDependencyTypes(allDependencyTypes, dependencyTypes, latestFileName);
    } catch (IOException e) {
      logger.error("Error while creating dependency group types: " + e.getMessage());
    }
  }

  private Set<String> extractAllDependencyGroupTypes() throws IOException {
    Path dirPath = Paths.get(jsonDirPath);
    Set<String> allDependencyGroupTypes =
        insertStaticTables.extractUniqueValues(
            mapper, dirPath, DependencyGroupTypeCreator::getDependencyGroupTypes);
    return allDependencyGroupTypes;
  }

  private List<DependencyType> getDependencyTypes(
      List<DependencyGroupTypeDTO> dependencyGroupTypes) {
    return dependencyGroupTypes.stream()
        .flatMap(
            dependencyGroupType ->
                dependencyGroupType.getDependencyTypes().stream()
                    .map(
                        dependencyType -> {
                          DependencyType depType = new DependencyType();
                          depType.setName(dependencyType.getName());
                          depType.setDependencyGroupType(
                              dependencyGroupTypeMapper.from(dependencyGroupType));
                          return depType;
                        }))
        .collect(Collectors.toList());
  }

  private void compareDependencyGroupTypes(
      Set<String> allDependencyGroupTypes,
      List<DependencyGroupTypeDTO> dependencyGroupTypes,
      String newFileName) {
    Set<String> dependencyGroupTypesForDeletion = new HashSet<>();
    Set<String> foundDependencyGroupTypes = new HashSet<>();
    for (DependencyGroupTypeDTO dependencyGroupType : dependencyGroupTypes) {
      if (allDependencyGroupTypes.contains(dependencyGroupType.getName())) {
        logger.info("Dependency group type found: " + dependencyGroupType.getName());
        foundDependencyGroupTypes.add(dependencyGroupType.getName());
      } else {
        logger.info("Dependency group type not found: " + dependencyGroupType.getName());
        dependencyGroupTypesForDeletion.add(dependencyGroupType.getName());
      }
    }
    if (dependencyGroupTypesForDeletion.size() > 0) {
      logger.info("Dependency group types for deletion: " + dependencyGroupTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteDependencyGroupSQL(dependencyGroupTypesForDeletion), true);
    }
    allDependencyGroupTypes.removeAll(foundDependencyGroupTypes);
    if (allDependencyGroupTypes.size() > 0) {
      logger.info("Dependency group types for insertion: " + allDependencyGroupTypes);
      insertStaticTables.createSQLFile(
          newFileName, buildInsertDependencyGroupTypeSQL(allDependencyGroupTypes), true);
    }
  }

  private void compareDependencyTypes(
      Map<String, Set<String>> allDependencyTypes,
      List<DependencyType> dependencyTypes,
      String newFileName) {
    Map<String, Set<String>> dependencyTypesForDeletion = new HashMap<>();
    Map<String, Set<String>> foundDependencyTypes = new HashMap<>();

    for (DependencyType dependencyType : dependencyTypes) {
      Set<String> values =
          allDependencyTypes.getOrDefault(
              dependencyType.getDependencyGroupType().getName(), new HashSet<>());
      if (values.contains(dependencyType.getName())) {
        logger.info("Dependency type found: " + dependencyType.getName());
        foundDependencyTypes
            .computeIfAbsent(
                dependencyType.getDependencyGroupType().getName(), k -> new HashSet<>())
            .add(dependencyType.getName());
      } else {
        logger.info("Dependency type not found: " + dependencyType.getName());
        dependencyTypesForDeletion
            .computeIfAbsent(
                dependencyType.getDependencyGroupType().getName(), k -> new HashSet<>())
            .add(dependencyType.getName());
      }
    }

    if (!dependencyTypesForDeletion.isEmpty()) {
      logger.info("Dependency types for deletion: " + dependencyTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteDependencySQL(dependencyTypesForDeletion), true);
    }

    allDependencyTypes.forEach(
        (key, value) ->
            value.removeAll(foundDependencyTypes.getOrDefault(key, Collections.emptySet())));

    if (!allDependencyTypes.isEmpty()) {
      logger.info("Dependency types for insertion: " + allDependencyTypes);
      insertStaticTables.createSQLFile(
          newFileName, buildInsertDependenciesTypeSQL(allDependencyTypes), true);
    }
  }

  static Set<String> getDependencyGroupTypes(Models models) {
    Set<String> dependencyGroups = new HashSet<>();
    for (Model model : models.getModels()) {
      if (model.getMetadata().getDependencyGroup() == null) {
        continue;
      }
      String dependencyGroup = model.getMetadata().getDependencyGroup();
      dependencyGroups.add(dependencyGroup);
    }
    return dependencyGroups;
  }

  public Map<String, Set<String>> getDependencies() {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Set<String>> groupDependencyMap = new HashMap<>();
    try {
      groupDependencyMap =
          objectMapper.readValue(
              new File(dependencyTypesJsonPath), new TypeReference<Map<String, Set<String>>>() {});
    } catch (IOException e) {
      logger.error("Error reading group_dependencies.json file: " + e.getMessage(), e);
    }
    return groupDependencyMap;
  }

  public static String buildInsertDependencyGroupTypeSQL(Set<String> dependencyGroupTypes) {
    StringBuilder sb = new StringBuilder();
    for (String dependencyGroupType : dependencyGroupTypes) {
      sb.append("INSERT INTO dependency_group_type(name) VALUES ('")
          .append(dependencyGroupType)
          .append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(dependencyGroupType, "dependency_group_type", 0));
    }
    return sb.toString();
  }

  public static String buildDeleteDependencyGroupSQL(Set<String> dependencyGroupTypes) {
    StringBuilder sb = new StringBuilder();
    for (String dependencyGroup : dependencyGroupTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(dependencyGroup, "dependency_group_type", 2));
      sb.append("DELETE FROM dependency_group_type WHERE name='")
          .append(dependencyGroup)
          .append("';\n");
    }
    return sb.toString();
  }

  public static String buildDeleteDependencySQL(Map<String, Set<String>> dependencyGroupTypes) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Set<String>> entry : dependencyGroupTypes.entrySet()) {
      Set<String> value = entry.getValue();
      for (String val : value) {
        sb.append(buildRevInfoInsertSQL());
        sb.append(buildInsertDependencyTypeAuditSQL(val, entry.getKey(), 2));
        sb.append("DELETE FROM dependency_type WHERE name='").append(val).append("';\n");
      }
    }
    return sb.toString();
  }

  public static String buildInsertDependenciesTypeSQL(Map<String, Set<String>> groupDependencyMap) {
    StringBuilder sb = new StringBuilder();
    logger.info(groupDependencyMap);
    for (Map.Entry<String, Set<String>> entry : groupDependencyMap.entrySet()) {
      String key = entry.getKey();
      Set<String> value = entry.getValue();
      for (String val : value) {
        sb.append("INSERT INTO dependency_type(name,dependency_group_id) VALUES ('")
            .append(val)
            .append("',(select id from dependency_group_type where name='")
            .append(key)
            .append("'));\n");
        sb.append(buildRevInfoInsertSQL());
        sb.append(buildInsertDependencyTypeAuditSQL(val, key, 0));
      }
    }
    return sb.toString();
  }

  public static String buildInsertDependencyTypeAuditSQL(
      String dependencyType, String dependencyGroupType, int revType) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append(
            "INSERT INTO dependency_type_aud(id, rev, revtype, name, dependency_group_id, created_at, updated_at) VALUES (")
        .append("(select id from dependency_type where name = '")
        .append(dependencyType)
        .append("'), (select max(rev) from revinfo),")
        .append(revType)
        .append(", '")
        .append(dependencyType)
        .append("', (select id from dependency_group_type where name = '")
        .append(dependencyGroupType)
        .append("'), '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }
}
