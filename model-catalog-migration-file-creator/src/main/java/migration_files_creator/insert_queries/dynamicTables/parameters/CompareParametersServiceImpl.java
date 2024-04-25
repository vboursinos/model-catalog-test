package migration_files_creator.insert_queries.dynamicTables.parameters;

import database.dto.ParameterDTO;
import database.dto.ParameterTypeDefinitionDTO;
import migration_files_creator.insert_queries.dynamicTables.parameters.typeParameters.ParameterStrategy;
import migration_files_creator.insert_queries.dynamicTables.parameters.typeParameters.TypeParameterStrategyFactoryImpl;
import migration_files_creator.model.HyperParameter;
import migration_files_creator.model.ParameterTypeDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompareParametersServiceImpl implements CompareParametersService {

  @Autowired private TypeParameterStrategyFactoryImpl typeParameterStrategyFactory;

  public boolean compareParameterTypeDefinition(
      ParameterTypeDistribution parameterType,
      ParameterTypeDefinitionDTO dbParameterTypeDefinition,
      String parameterName) {
    return parameterType.getParameterType().equals(dbParameterTypeDefinition.getType().getName())
        && parameterType
            .getParameterDistribution()
            .equals(dbParameterTypeDefinition.getDistribution().getName())
        && parameterType.getParameterName().equals(parameterName);
  }

  public boolean compareParameterColumns(HyperParameter hyperParameter, ParameterDTO parameter) {
    return hyperParameter.getDescription().equals(parameter.getDescription())
        && hyperParameter.getLabel().equals(parameter.getLabel())
        && hyperParameter.isFixedValue() == parameter.getFixedValue()
        && hyperParameter.getEnabled().equals(parameter.getEnabled());
  }

  public boolean compareTypeParameter(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      ParameterDTO dbParameter) {

    for (ParameterTypeDefinitionDTO dbParameterTypeDefinition : dbParameter.getDefinitions()) {

      ParameterStrategy strategy =
          typeParameterStrategyFactory.getParameterStrategy(
              parameterTypeDistribution.getParameterType());

      if (strategy != null) {
        return strategy.compareTypeParameter(
            parameter, parameterTypeDistribution, dbParameter, dbParameterTypeDefinition);
      }
    }
    return false;
  }
}
