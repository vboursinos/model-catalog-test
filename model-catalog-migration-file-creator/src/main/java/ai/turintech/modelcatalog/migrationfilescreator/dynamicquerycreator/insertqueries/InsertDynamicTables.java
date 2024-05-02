package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries;

import java.util.List;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;


public interface InsertDynamicTables {
  public String buildInsertSQL(Models models, List<ModelDTO> dbModels);
}
