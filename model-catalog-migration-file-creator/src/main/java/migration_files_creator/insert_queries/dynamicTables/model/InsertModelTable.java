package migration_files_creator.insert_queries.dynamicTables.model;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.List;
import migration_files_creator.model.Model;

public interface InsertModelTable {
  public String buildInsertIntoModelSQL(Model jsonModel, List<ModelDTO> dbModelList);
}
