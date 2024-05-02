package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.List;
import ai.turintech.modelcatalog.migrationfilescreator.model.HyperParameter;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.model.ParameterTypeDistribution;

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
