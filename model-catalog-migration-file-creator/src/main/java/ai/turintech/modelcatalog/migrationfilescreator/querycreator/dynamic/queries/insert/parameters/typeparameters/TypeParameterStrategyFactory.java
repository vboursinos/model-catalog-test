package ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.parameters.typeparameters;

public interface TypeParameterStrategyFactory {

  public ParameterStrategy getParameterStrategy(String parameterType);
}
