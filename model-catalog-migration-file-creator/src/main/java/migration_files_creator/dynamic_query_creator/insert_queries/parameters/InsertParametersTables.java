package migration_files_creator.dynamic_query_creator.insert_queries.parameters;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.List;
import migration_files_creator.model.HyperParameter;
import migration_files_creator.model.Model;
import migration_files_creator.model.ParameterTypeDistribution;

public interface InsertParametersTables {

  public String compareTypeParameterAndInsert(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName);

  public String updateTypeParameter(
      HyperParameter parameter,
      ParameterTypeDistribution parameterTypeDistribution,
      String modelName);

  public String insertIntoParameterAndParameterValue(
      List<HyperParameter> orderedParameters, String name, List<ModelDTO> models);

  public String buildInsertIntoParameterAndParameterValueSQL(Model model, List<ModelDTO> models);
}
