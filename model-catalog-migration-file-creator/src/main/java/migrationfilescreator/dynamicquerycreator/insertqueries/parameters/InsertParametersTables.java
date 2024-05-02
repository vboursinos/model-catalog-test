package migrationfilescreator.dynamicquerycreator.insertqueries.parameters;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.List;
import migrationfilescreator.model.HyperParameter;
import migrationfilescreator.model.Model;
import migrationfilescreator.model.ParameterTypeDistribution;

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
