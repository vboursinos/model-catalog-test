package migration_files_creator.dynamic_query_creator.insert_queries.model.pivotTables;

import ai.turintech.modelcatalog.dto.ModelDTO;
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
