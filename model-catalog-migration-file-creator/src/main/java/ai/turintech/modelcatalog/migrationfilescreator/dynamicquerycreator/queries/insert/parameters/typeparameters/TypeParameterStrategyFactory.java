package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.typeparameters;

public interface TypeParameterStrategyFactory {

  public ParameterStrategy getParameterStrategy(String parameterType);
}
