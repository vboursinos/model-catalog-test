package migrationfilescreator.dynamicquerycreator.insertqueries.parameters.typeParameters;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeParameterStrategyFactoryImpl implements TypeParameterStrategyFactory {

  @Autowired private List<ParameterStrategy> parameterStrategies;

  public ParameterStrategy getParameterStrategy(String parameterType) {
    for (ParameterStrategy parameterStrategy : parameterStrategies) {
      if (parameterStrategy.getParameterTypeName().equals(parameterType)) {
        return parameterStrategy;
      }
    }
    return null;
  }
}
