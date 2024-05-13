package ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.service.ParameterTypeService;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParameterTypeCreator extends TableCreatorHelper implements StaticTableCreator {
  private static final Logger logger = LogManager.getLogger(ParameterTypeCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Value("${json_dir_path}")
  private String jsonDirPath;

  @Value("${parameter_types}")
  private String parameterTypeProperty;

  @Autowired private ParameterTypeService parameterTypeService;

  public String createStaticTable() {
    Set<String> allParameterTypes;
    String parameterTypeSql = "";
    try {
      allParameterTypes = extractAllParameterTypes();
      List<ParameterTypeDTO> parameterTypes = parameterTypeService.findAll().block();
      logger.info("Parameter types: " + parameterTypes);
      parameterTypeSql = compareParameterTypes(allParameterTypes, parameterTypes);
    } catch (IOException e) {
      logger.error("Error while creating parameter types: " + e.getMessage());
    }
    return parameterTypeSql;
  }

  private Set<String> extractAllParameterTypes() throws IOException {
    Set<String> allParameterTypes = getParameterTypes();
    return allParameterTypes;
  }

  private String compareParameterTypes(
      Set<String> allParameterTypes, List<ParameterTypeDTO> parameterTypes) {
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
      return buildDeleteParameterTypeSQL(parameterTypesForDeletion);
    }
    allParameterTypes.removeAll(foundParameterTypes);
    if (allParameterTypes.size() > 0) {
      logger.info("Parameter types for insertion: " + allParameterTypes);
      return buildParameterTypeSQL(allParameterTypes);
    }
    return "\n";
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

  public Set<String> getParameterTypes() {
    Set<String> parameterTypes = new HashSet<>(Arrays.asList(parameterTypeProperty.split(",")));
    return parameterTypes;
  }
}
