package ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import java.util.List;

public interface InsertDynamicTables {
  public String buildInsertSQL(Models models, List<ModelDTO> dbModels);
}
