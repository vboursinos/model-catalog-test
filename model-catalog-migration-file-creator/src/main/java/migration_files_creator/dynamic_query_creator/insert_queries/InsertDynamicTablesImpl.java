package migration_files_creator.dynamic_query_creator.insert_queries;

import static utils.Transformations.replaceSingleQuotesInMetadata;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.*;
import migration_files_creator.dynamic_query_creator.insert_queries.model.InsertModelTable;
import migration_files_creator.dynamic_query_creator.insert_queries.model.pivotTables.Pivot;
import migration_files_creator.dynamic_query_creator.insert_queries.model.pivotTables.PivotStrategyFactory;
import migration_files_creator.dynamic_query_creator.insert_queries.parameters.InsertParametersTables;
import migration_files_creator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertDynamicTablesImpl implements InsertDynamicTables{
  @Autowired private InsertParametersTables insertParametersTables;
  @Autowired private InsertModelTable insertModelTable;

  @Autowired private PivotStrategyFactory pivotStrategyFactory;
  @Autowired private List<Pivot> pivotTables;

  public String buildInsertSQL(Models models, List<ModelDTO> dbModels) {
    StringBuilder sb = new StringBuilder();
    for (Model model : models.getModels()) {
      processModel(model, sb, dbModels);
    }

    return sb.toString();
  }

  private void processModel(Model model, StringBuilder sb, List<ModelDTO> models) {
    Metadata metadata = model.getMetadata();
    replaceSingleQuotesInMetadata(metadata);

    sb.append(insertModelTable.buildInsertIntoModelSQL(model, models));
    for (Pivot pivot : pivotTables) {
      sb.append(pivot.buildInsertIntoPivotSQL(model, models));
    }
    sb.append(insertParametersTables.buildInsertIntoParameterAndParameterValueSQL(model, models));

    //    sb.append(buildInsertConstraintSQL(model));
  }
}
