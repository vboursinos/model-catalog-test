package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.model.pivotTables;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.List;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;

public interface Pivot {
  public String buildInsertIntoPivotSQL(Model model, List<ModelDTO> dbModelList);

  public String buildDeleteSQLPivotTableNotExist(
      ModelDTO dbModel, Model jsonModel);

  public String buildInsertIntoPivotDeleteSQL(ModelDTO model);

  public StringBuilder createPivotQuerySQL(String value, String modelName);

  public String createDeletePivotQuerySQL(ModelDTO model);

  public String createPivotQueryAuditSQL(String value, String modelName, int action);

  public String getTableName();
}
