package migration_files_creator.dynamic_query_creator.insert_queries;


import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.*;
import migration_files_creator.model.*;

public interface InsertDynamicTables {
  public String buildInsertSQL(Models models, List<ModelDTO> dbModels);
}
