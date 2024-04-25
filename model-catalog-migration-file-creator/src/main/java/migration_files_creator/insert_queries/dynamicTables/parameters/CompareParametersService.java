package migration_files_creator.insert_queries.dynamicTables.parameters;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import migration_files_creator.model.HyperParameter;
import migration_files_creator.model.ParameterTypeDistribution;

public interface CompareParametersService {

  public boolean compareParameterTypeDefinition(
      ParameterTypeDistribution parameterType,
      ParameterTypeDefinitionDTO dbParameterTypeDefinition,
      String parameterName);

  public boolean compareParameterColumns(HyperParameter hyperParameter, ParameterDTO parameter);

  public boolean compareTypeParameter(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      ParameterDTO dbParameter);
}
