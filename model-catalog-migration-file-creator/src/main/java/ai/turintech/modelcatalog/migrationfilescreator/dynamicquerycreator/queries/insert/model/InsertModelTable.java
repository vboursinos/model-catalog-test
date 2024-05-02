package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.model;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.List;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;

public interface InsertModelTable {
  public String buildInsertIntoModelSQL(Model jsonModel, List<ModelDTO> dbModelList);
}
