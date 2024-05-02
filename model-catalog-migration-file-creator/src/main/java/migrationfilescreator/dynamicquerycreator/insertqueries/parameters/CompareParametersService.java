package migrationfilescreator.dynamicquerycreator.insertqueries.parameters;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import migrationfilescreator.model.HyperParameter;
import migrationfilescreator.model.ParameterTypeDistribution;

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
