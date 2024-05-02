package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.delete;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.Models;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;
import java.util.List;
import java.util.Set;

public interface DeleteDynamicTables {

  public String buildDeleteSQL(String mltask, Models models, List<ModelDTO> modelsDTO);

  public String buildDeleteSQLModelNotExist(String mltask, List<ModelDTO> modelsDTO);

  public String addAUDCommandsIfModelNotExist(ModelDTO model);

  public String buildDeleteSQLParameterNotExist(ModelDTO dbModel, Model jsonModel);

  public String buildDeleteSQLParameterNotExist(
      List<ParameterDTO> parametersForDeletion, ModelDTO model);

  public String addAUDCommandsIfParameterNotExist(ParameterDTO parameter, ModelDTO model);

  public String buildDeleteSQLParameterTypeDefinitionNotExist(
      Set<ParameterTypeDefinitionDTO> parameterTypeDefinitions,
      List<ParameterTypeDistribution> parameterTypeDistributions,
      String model,
      String parameterName);
}
