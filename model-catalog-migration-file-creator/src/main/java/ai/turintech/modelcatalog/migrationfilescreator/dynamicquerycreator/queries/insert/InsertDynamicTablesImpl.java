package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert;

import static ai.turintech.modelcatalog.migrationfilescreator.utils.Transformations.replaceSingleQuotesInMetadata;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.model.InsertModelTable;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.model.pivottables.Pivot;
import ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.InsertParametersTables;
import ai.turintech.modelcatalog.migrationfilescreator.model.Metadata;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertDynamicTablesImpl implements InsertDynamicTables {
  @Autowired private InsertParametersTables insertParametersTables;
  @Autowired private InsertModelTable insertModelTable;

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
