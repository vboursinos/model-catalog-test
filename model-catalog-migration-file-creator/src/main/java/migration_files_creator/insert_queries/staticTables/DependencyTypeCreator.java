package migration_files_creator.insert_queries.staticTables;

import ai.turintech.modelcatalog.entity.DependencyType;
import ai.turintech.modelcatalog.repository.DependencyTypeRepository;
import ai.turintech.modelcatalog.service.DependencyTypeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DependencyTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private static final Logger logger = LogManager.getLogger(DependencyTypeCreator.class);

  private static final String DEPENDENCY_TYPES_JSON_FILE_PATH =
      "model-catalog-migration-file-creator/static/group_dependencies.json";

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();
  @Autowired private DependencyTypeRepository dependencyTypeRepository;

  public void createStaticTable(String newFileName) {
    Map<String, Set<String>> allDependencyTypes = getDependencies();
    List<DependencyType> dependencyTypes = dependencyTypeRepository.findAll();
    logger.info("Dependency types: " + dependencyTypes);
    compareDependencyTypes(allDependencyTypes, dependencyTypes, newFileName);
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

  static String buildDeleteDependencySQL(Map<String, Set<String>> dependencyGroupTypes) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Set<String>> entry : dependencyGroupTypes.entrySet()) {
      Set<String> value = entry.getValue();
      for (String val : value) {
        sb.append(buildRevInfoInsertSQL());
        sb.append(buildDeleteAuditSQL(val, entry.getKey(), 2));
        sb.append("DELETE FROM dependency_type WHERE name='").append(val).append("';\n");
      }
    }
    return sb.toString();
  }

  static Map<String, Set<String>> getDependencies() {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Set<String>> groupDependencyMap = new HashMap<>();
    try {
      groupDependencyMap =
          objectMapper.readValue(
              new File(DEPENDENCY_TYPES_JSON_FILE_PATH),
              new TypeReference<Map<String, Set<String>>>() {});
    } catch (IOException e) {
      logger.error("Error reading group_dependencies.json file: " + e.getMessage(), e);
    }
    return groupDependencyMap;
  }

  static String buildInsertDependenciesTypeSQL(Map<String, Set<String>> groupDependencyMap) {
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
        sb.append(buildInsertAuditSQL(val, key, 0));
      }
    }
    return sb.toString();
  }

  public static String buildInsertAuditSQL(
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
