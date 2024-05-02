package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.parameters.typeParameters;

public interface TypeParameterStrategyFactory {

  public ParameterStrategy getParameterStrategy(String parameterType);
}
