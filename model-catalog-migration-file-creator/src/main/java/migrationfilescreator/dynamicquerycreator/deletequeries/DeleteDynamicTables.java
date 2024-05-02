package migrationfilescreator.dynamicquerycreator.deletequeries;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.List;
import java.util.Set;
import migrationfilescreator.model.Models;
import migrationfilescreator.model.ParameterTypeDistribution;

public interface DeleteDynamicTables {

  public String buildDeleteSQL(String mltask, Models models, List<ModelDTO> modelsDTO);

  public String buildDeleteSQLModelNotExist(String mltask, List<ModelDTO> modelsDTO);

  public String addAUDCommandsIfModelNotExist(ModelDTO model);

  public String buildDeleteSQLParameterNotExist(
      ModelDTO dbModel, migrationfilescreator.model.Model jsonModel);

  public String buildDeleteSQLParameterNotExist(
      List<ParameterDTO> parametersForDeletion, ModelDTO model);

  public String addAUDCommandsIfParameterNotExist(ParameterDTO parameter, ModelDTO model);

  public String buildDeleteSQLParameterTypeDefinitionNotExist(
      Set<ParameterTypeDefinitionDTO> parameterTypeDefinitions,
      List<ParameterTypeDistribution> parameterTypeDistributions,
      String model,
      String parameterName);
}
