package migration_files_creator.insert_queries.staticTables;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.service.MlTaskTypeService;
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
public class MlTaskCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(MlTaskCreator.class);
  private final InsertStaticTables insertStaticTables = new InsertStaticTables();
  @Autowired private MlTaskTypeService mlTaskTypeService;

  public void createStaticTable(String newFileName) {
    Set<String> allMlTaskTypes;
    try {
      allMlTaskTypes = extractAllMlTaskTypes();
      List<MlTaskTypeDTO> mlTaskTypes = mlTaskTypeService.findAll().block();
      logger.info("Mltask types: " + mlTaskTypes);
      compareMlTaskTypes(allMlTaskTypes, mlTaskTypes, newFileName);
      ;
    } catch (IOException e) {
      logger.error("Error while creating MlTask types: " + e.getMessage());
    }
  }

  private Set<String> extractAllMlTaskTypes() throws IOException {
    Path dirPath = Paths.get(insertStaticTables.getJsonDirPath());
    InsertStaticTables insertStaticTables = new InsertStaticTables();
    Set<String> allMlTaskTypes =
        insertStaticTables.extractUniqueValues(mapper, dirPath, MlTaskCreator::getMlTaskTypes);
    return allMlTaskTypes;
  }

  private void compareMlTaskTypes(
      Set<String> allMlTaskTypes, List<MlTaskTypeDTO> mlTaskTypes, String newFileName) {
    Set<String> mlTaskTypesForDeletion = new HashSet<>();
    Set<String> foundMlTaskTypes = new HashSet<>();
    for (MlTaskTypeDTO mlTaskType : mlTaskTypes) {
      if (allMlTaskTypes.contains(mlTaskType.getName())) {
        logger.info("MlTask type found: " + mlTaskType.getName());
        foundMlTaskTypes.add(mlTaskType.getName());
      } else {
        logger.info("MlTask type not found: " + mlTaskType.getName());
        mlTaskTypesForDeletion.add(mlTaskType.getName());
      }
    }
    if (mlTaskTypesForDeletion.size() > 0) {
      logger.info("MlTask types for deletion: " + mlTaskTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteMlTaskTypesSQL(mlTaskTypesForDeletion), true);
    }
    allMlTaskTypes.removeAll(foundMlTaskTypes);
    if (allMlTaskTypes.size() > 0) {
      logger.info("MlTask types for insertion: " + allMlTaskTypes);
      insertStaticTables.createSQLFile(newFileName, buildMlTaskTypesSQL(allMlTaskTypes), true);
    }
  }

  static String buildMlTaskTypesSQL(Set<String> mlTaskSet) {
    StringBuilder sb = new StringBuilder();
    for (String mlTask : mlTaskSet) {
      sb.append("INSERT INTO ml_task_type(name) VALUES ('").append(mlTask).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(mlTask, "ml_task_type", 0));
    }
    return sb.toString();
  }

  static String buildDeleteMlTaskTypesSQL(Set<String> mlTaskTypes) {
    StringBuilder sb = new StringBuilder();
    for (String mlTask : mlTaskTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(mlTask, "ml_task_type", 2));
      sb.append("DELETE FROM ml_task_type WHERE name='").append(mlTask).append("';\n");
    }
    return sb.toString();
  }

  static Set<String> getMlTaskTypes(Models models) {
    Set<String> mlTaskSet = new HashSet<>();
    for (Model model : models.getModels()) {
      String mlTask = model.getMlTask();
      mlTaskSet.add(mlTask);
    }
    return mlTaskSet;
  }
}
