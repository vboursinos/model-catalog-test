package migration_files_creator.delete_queries;

import database.dto.ModelDTO;
import database.dto.ParameterDTO;
import database.dto.ParameterTypeDefinitionDTO;
import java.util.List;
import java.util.Set;
import migration_files_creator.model.Models;
import migration_files_creator.model.ParameterTypeDistribution;

public interface DeleteDynamicTables {

  public String buildDeleteSQL(String mltask, Models models, List<ModelDTO> modelsDTO);

  public String buildDeleteSQLModelNotExist(String mltask, List<ModelDTO> modelsDTO);

  public String addAUDCommandsIfModelNotExist(ModelDTO model);

  public String buildDeleteSQLParameterNotExist(
      ModelDTO dbModel, migration_files_creator.model.Model jsonModel);

  public String buildDeleteSQLParameterNotExist(
      List<ParameterDTO> parametersForDeletion, ModelDTO model);

  public String addAUDCommandsIfParameterNotExist(ParameterDTO parameter, ModelDTO model);

  public String buildDeleteSQLParameterTypeDefinitionNotExist(
      Set<ParameterTypeDefinitionDTO> parameterTypeDefinitions,
      List<ParameterTypeDistribution> parameterTypeDistributions,
      String model,
      String parameterName);
}
