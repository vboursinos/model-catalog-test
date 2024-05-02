package migrationfilescreator.dynamicquerycreator.insertqueries;

import static utils.Transformations.replaceSingleQuotesInMetadata;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.*;
import migrationfilescreator.dynamicquerycreator.insertqueries.model.InsertModelTable;
import migrationfilescreator.dynamicquerycreator.insertqueries.model.pivotTables.Pivot;
import migrationfilescreator.dynamicquerycreator.insertqueries.model.pivotTables.PivotStrategyFactory;
import migrationfilescreator.dynamicquerycreator.insertqueries.parameters.InsertParametersTables;
import migrationfilescreator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertDynamicTablesImpl implements InsertDynamicTables {
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
  }
}
