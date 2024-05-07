package ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.EnsembleFamily;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import java.util.List;

public interface InsertModelTable {
  public String buildInsertIntoModelSQL(Model jsonModel, List<ModelDTO> dbModelList);

  public boolean isDecisionTreeModel(String modelName);

  public EnsembleFamily getEnsembleFamily(String modelName);
}
