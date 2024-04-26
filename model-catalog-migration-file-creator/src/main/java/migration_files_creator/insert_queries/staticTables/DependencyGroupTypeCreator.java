package migration_files_creator.insert_queries.staticTables;

import ai.turintech.modelcatalog.dto.DependencyGroupTypeDTO;
import ai.turintech.modelcatalog.service.DependencyGroupTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DependencyGroupTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(DependencyGroupTypeCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private DependencyGroupTypeService dependencyGroupTypeService;

  public void createStaticTable(String latestFileName) {
    Set<String> allDependencyGroupTypes;
    try {
      allDependencyGroupTypes = extractAllDependencyGroupTypes();
      List<DependencyGroupTypeDTO> dependencyGroupTypes =
          dependencyGroupTypeService.findAll().block();
      logger.info("Dependency group types: " + dependencyGroupTypes);
      compareDependencyGroupTypes(allDependencyGroupTypes, dependencyGroupTypes, latestFileName);
    } catch (IOException e) {
      logger.error("Error while creating dependency group types: " + e.getMessage());
    }
  }

  private Set<String> extractAllDependencyGroupTypes() throws IOException {
    Path dirPath = Paths.get(insertStaticTables.getJsonDirPath());
    Set<String> allDependencyGroupTypes =
        insertStaticTables.extractUniqueValues(
            mapper, dirPath, DependencyGroupTypeCreator::getDependencyGroupTypes);
    return allDependencyGroupTypes;
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

  static String buildInsertDependencyGroupTypeSQL(Set<String> dependencyGroupTypes) {
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

  static String buildDeleteDependencyGroupSQL(Set<String> dependencyGroupTypes) {
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
}
