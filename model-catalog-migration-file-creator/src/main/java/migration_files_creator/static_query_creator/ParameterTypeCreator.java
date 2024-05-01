package migration_files_creator.static_query_creator;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.service.ParameterTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.PropertiesConfig;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import migration_files_creator.model.Models;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParameterTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(ParameterTypeCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Autowired private ParameterTypeService parameterTypeService;

  public void createStaticTable(String newFileName) {
    Set<String> allParameterTypes;
    try {
      allParameterTypes = extractAllParameterTypes();
      List<ParameterTypeDTO> parameterTypes = parameterTypeService.findAll().block();
      logger.info("Parameter types: " + parameterTypes);
      compareParameterTypes(allParameterTypes, parameterTypes, newFileName);
    } catch (IOException e) {
      logger.error("Error while creating parameter types: " + e.getMessage());
    }
  }

  private Set<String> extractAllParameterTypes() throws IOException {
    Path dirPath = Paths.get(insertStaticTables.getJsonDirPath());
    InsertStaticTables insertStaticTables = new InsertStaticTables();
    Set<String> allParameterTypes =
        insertStaticTables.extractUniqueValues(
            mapper, dirPath, ParameterTypeCreator::getParameterTypes);
    return allParameterTypes;
  }

  private void compareParameterTypes(
      Set<String> allParameterTypes, List<ParameterTypeDTO> parameterTypes, String newFileName) {
    Set<String> parameterTypesForDeletion = new HashSet<>();
    Set<String> foundParameterTypes = new HashSet<>();
    for (ParameterTypeDTO parameterType : parameterTypes) {
      if (allParameterTypes.contains(parameterType.getName())) {
        logger.info("Parameter type found: " + parameterType.getName());
        foundParameterTypes.add(parameterType.getName());
      } else {
        logger.info("Parameter type not found: " + parameterType.getName());
        parameterTypesForDeletion.add(parameterType.getName());
      }
    }
    if (parameterTypesForDeletion.size() > 0) {
      logger.info("Parameter types for deletion: " + parameterTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName, buildDeleteParameterTypeSQL(parameterTypesForDeletion), true);
    }
    allParameterTypes.removeAll(foundParameterTypes);
    if (allParameterTypes.size() > 0) {
      logger.info("Parameter types for insertion: " + allParameterTypes);
      insertStaticTables.createSQLFile(newFileName, buildParameterTypeSQL(allParameterTypes), true);
    }
  }

  public static String buildParameterTypeSQL(Set<String> parameterTypes) {
    StringBuilder sb = new StringBuilder();
    for (String parameterType : parameterTypes) {
      sb.append("INSERT INTO parameter_type(name) VALUES ('").append(parameterType).append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(parameterType, "parameter_type", 0));
    }
    return sb.toString();
  }

  public static String buildDeleteParameterTypeSQL(Set<String> parameterTypes) {
    StringBuilder sb = new StringBuilder();
    for (String parameterType : parameterTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(parameterType, "parameter_type", 2));
      sb.append("DELETE FROM parameter_type WHERE name='").append(parameterType).append("';\n");
    }
    return sb.toString();
  }

  static Set<String> getParameterTypes(Models models) {
    Properties properties = PropertiesConfig.getProperties();
    String parameterTypeProperty = properties.getProperty("parameter_types");
    Set<String> parameterTypes = new HashSet<>(Arrays.asList(parameterTypeProperty.split(",")));
    return parameterTypes;
  }
}
