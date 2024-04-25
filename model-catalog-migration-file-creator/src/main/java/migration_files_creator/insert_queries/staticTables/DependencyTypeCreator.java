package migration_files_creator.insert_queries.staticTables;

import ai.turintech.modelcatalog.dto.DependencyTypeDTO;
import ai.turintech.modelcatalog.service.DependencyTypeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DependencyTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private static final Logger logger = LogManager.getLogger(DependencyTypeCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  private Map<String, Map<String, Set<String>>> foundDependencyTypes = new HashMap<>();
  private Set<String> oldDependencyTypes = new HashSet<>();

  @Autowired private DependencyTypeService dependencyTypeService;

  public void createStaticTable() {
    Map<String, Set<String>> allDependencyTypes = getDependencies();
    Mono<List<DependencyTypeDTO>> dependencyTypesMono = dependencyTypeService.findAll();
    List<DependencyTypeDTO> dependencyTypes = dependencyTypesMono.block();
    logger.info("Dependency types: " + dependencyTypes);
    compareDependencyTypes(allDependencyTypes, dependencyTypes);
  }

  private void compareDependencyTypes(
      Map<String, Set<String>> allDependencyTypes, List<DependencyTypeDTO> dependencyTypes) {
    String newFileName = insertStaticTables.getFilename();
    Map<String, Set<String>> dependencyTypesForDeletion = new HashMap<>();
    Map<String, Set<String>> foundDependencyTypes = new HashMap<>();

    for (DependencyTypeDTO dependencyType : dependencyTypes) {
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
    String filePath = Paths.get("static", "group_dependencies.json").toString();
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Set<String>> groupDependencyMap = new HashMap<>();
    try {
      groupDependencyMap =
          objectMapper.readValue(
              new File(filePath), new TypeReference<Map<String, Set<String>>>() {});
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
