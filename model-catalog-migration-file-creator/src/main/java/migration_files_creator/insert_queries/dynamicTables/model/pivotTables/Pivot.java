package migration_files_creator.insert_queries.dynamicTables.model.pivotTables;

import database.dto.ModelDTO;
import java.util.List;
import migration_files_creator.model.Model;

public interface Pivot {
  public String buildInsertIntoPivotSQL(Model model, List<ModelDTO> dbModelList);

  public String buildDeleteSQLPivotTableNotExist(
      ModelDTO dbModel, migration_files_creator.model.Model jsonModel);

  public String buildInsertIntoPivotDeleteSQL(ModelDTO model);

  public StringBuilder createPivotQuerySQL(String value, String modelName);

  public String createDeletePivotQuerySQL(ModelDTO model);

  public String createPivotQueryAuditSQL(String value, String modelName, int action);

  public String getTableName();
}
