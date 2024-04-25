package migration_files_creator.insert_queries.dynamicTables.parameters.typeParameters;

public interface TypeParameterStrategyFactory {

  public ParameterStrategy getParameterStrategy(String parameterType);
}
