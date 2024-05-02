package migrationfilescreator.staticquerycreator;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.service.ParameterDistributionTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import migrationfilescreator.dynamicquerycreator.insertqueries.parameters.InsertParametersTablesImpl;
import migrationfilescreator.model.HyperParameter;
import migrationfilescreator.model.Model;
import migrationfilescreator.model.Models;
import migrationfilescreator.model.ParameterTypeDistribution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParameterDistributionTypeCreator extends TableCreatorHelper
    implements StaticTableCreator {
  private final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(ParameterDistributionTypeCreator.class);

  private final InsertStaticTables insertStaticTables = new InsertStaticTables();

  @Value("${json_dir_path}")
  private String jsonDirPath;

  @Autowired private ParameterDistributionTypeService parameterDistributionTypeService;

  public void createStaticTable(String newFileName) {
    Set<String> allParameterDistributionTypes;
    try {
      allParameterDistributionTypes = extractAllParameterDistributionTypes();
      List<ParameterDistributionTypeDTO> parameterDistributionTypes =
          parameterDistributionTypeService.findAll().block();
      logger.info("Parameter Distribution types: " + parameterDistributionTypes);
      compareParameterDistributionTypes(
          allParameterDistributionTypes, parameterDistributionTypes, newFileName);
    } catch (IOException e) {
      logger.error("Error while creating parameter distribution types: " + e.getMessage());
    }
  }

  private Set<String> extractAllParameterDistributionTypes() throws IOException {
    Path dirPath = Paths.get(jsonDirPath);
    InsertStaticTables insertStaticTables = new InsertStaticTables();
    Set<String> allParameterDistributionTypes =
        insertStaticTables.extractUniqueValues(
            mapper, dirPath, ParameterDistributionTypeCreator::getParameterDistributionTypes);
    return allParameterDistributionTypes;
  }

  private void compareParameterDistributionTypes(
      Set<String> allParameterDistributionTypes,
      List<ParameterDistributionTypeDTO> parameterDistributionTypes,
      String newFileName) {
    Set<String> parameterDistributionTypesForDeletion = new HashSet<>();
    Set<String> foundParameterDistributionTypes = new HashSet<>();
    for (ParameterDistributionTypeDTO parameterDistributionType : parameterDistributionTypes) {
      if (allParameterDistributionTypes.contains(parameterDistributionType.getName())) {
        logger.info("Parameter distribution type found: " + parameterDistributionType.getName());
        foundParameterDistributionTypes.add(parameterDistributionType.getName());
      } else {
        logger.info(
            "Parameter distribution type not found: " + parameterDistributionType.getName());
        parameterDistributionTypesForDeletion.add(parameterDistributionType.getName());
      }
    }
    if (parameterDistributionTypesForDeletion.size() > 0) {
      logger.info(
          "Parameter distribution types for deletion: " + parameterDistributionTypesForDeletion);
      insertStaticTables.createSQLFile(
          newFileName,
          buildDeleteParameterDistributionTypeSQL(parameterDistributionTypesForDeletion),
          true);
    }
    allParameterDistributionTypes.removeAll(foundParameterDistributionTypes);
    if (allParameterDistributionTypes.size() > 0) {
      logger.info("Parameter distribution types for insertion: " + allParameterDistributionTypes);
      insertStaticTables.createSQLFile(
          newFileName, buildParameterDistributionTypeSQL(allParameterDistributionTypes), true);
    }
  }

  public static String buildParameterDistributionTypeSQL(Set<String> distributionTypes) {
    StringBuilder sb = new StringBuilder();
    for (String distributionType : distributionTypes) {
      sb.append("INSERT INTO parameter_distribution_type(name) VALUES ('")
          .append(distributionType)
          .append("');\n");
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildInsertAuditSQL(distributionType, "parameter_distribution_type", 0));
    }
    return sb.toString();
  }

  public static String buildDeleteParameterDistributionTypeSQL(Set<String> distributionTypes) {
    StringBuilder sb = new StringBuilder();
    for (String distributionType : distributionTypes) {
      sb.append(buildRevInfoInsertSQL());
      sb.append(buildDeleteAuditSQL(distributionType, "parameter_distribution_type", 2));
      sb.append("DELETE FROM parameter_distribution_type WHERE name='")
          .append(distributionType)
          .append("';\n");
    }
    return sb.toString();
  }

  static Set<String> getParameterDistributionTypes(Models models) {
    Set<String> parameterDistributionTypes = new HashSet<>();
    for (Model model : models.getModels()) {
      for (HyperParameter hyperParameter : model.getHyperParameters()) {
        List<ParameterTypeDistribution> distribution =
            InsertParametersTablesImpl.getParameterTypeDistributionList(hyperParameter);
        for (ParameterTypeDistribution parameterTypeDistribution : distribution) {
          parameterDistributionTypes.add(parameterTypeDistribution.getParameterDistribution());
        }
      }
    }
    return parameterDistributionTypes;
  }
}
