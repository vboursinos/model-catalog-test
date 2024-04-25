package migration_files_creator.insert_queries.dynamicTables;

import static utils.Transformations.replaceSingleQuotesInMetadata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.dto.ModelDTO;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import migration_files_creator.insert_queries.dynamicTables.model.InsertModelTable;
import migration_files_creator.insert_queries.dynamicTables.model.pivotTables.Pivot;
import migration_files_creator.insert_queries.dynamicTables.model.pivotTables.PivotStrategyFactory;
import migration_files_creator.insert_queries.dynamicTables.parameters.InsertParametersTables;
import migration_files_creator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertDynamicTables {
  private static final String ENSEMBLE_FAMILIES_FILE = "ensemble-family.json";
  private static final String ENSEMBLE_FAMILIES_DIR = "static";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired private InsertParametersTables insertParametersTables;

  @Autowired private InsertModelTable insertModelTable;

  @Autowired private PivotStrategyFactory pivotStrategyFactory;

  @Autowired private List<Pivot> pivotTables;

  public String buildInsertSQL(Models models, List<ModelDTO> dbModels) {
    List<EnsembleFamily> ensembleFamilies = getEnsembleFamilies();
    StringBuilder sb = new StringBuilder();
    for (Model model : models.getModels()) {
      processModel(model, ensembleFamilies, sb, dbModels);
    }

    return sb.toString();
  }

  private static List<EnsembleFamily> getEnsembleFamilies() {
    List<EnsembleFamily> ensembleFamilies = null;
    try {
      ensembleFamilies =
          objectMapper.readValue(
              Paths.get(ENSEMBLE_FAMILIES_DIR, ENSEMBLE_FAMILIES_FILE).toFile(),
              new TypeReference<List<EnsembleFamily>>() {});
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ensembleFamilies;
  }

  private void processModel(
      Model model, List<EnsembleFamily> ensembleFamilies, StringBuilder sb, List<ModelDTO> models) {
    String name = model.getName().replace("'", "''");
    Metadata metadata = model.getMetadata();
    replaceSingleQuotesInMetadata(metadata);

    sb.append(insertModelTable.buildInsertIntoModelSQL(model, ensembleFamilies, models));
    for (Pivot pivot : pivotTables) {
      sb.append(pivot.buildInsertIntoPivotSQL(model, models));
    }
    sb.append(insertParametersTables.buildInsertIntoParameterAndParameterValueSQL(model, models));

    //    sb.append(buildInsertConstraintSQL(model));
  }
}
