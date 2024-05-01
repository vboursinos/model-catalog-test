package migration_files_creator.dynamic_query_creator.insert_queries.parameters.typeParameters;

public interface TypeParameterStrategyFactory {

  public ParameterStrategy getParameterStrategy(String parameterType);
}
