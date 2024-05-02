package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.parameters.typeparameters;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TypeParameterUtils {
  boolean shouldDeleteParameterType(Object parameterDefaultValue, Object distributionDefaultValue) {
    return parameterDefaultValue == null && distributionDefaultValue != null
        || !Objects.equals(parameterDefaultValue, distributionDefaultValue);
  }

  boolean allCategoriesPresent(
      Set<CategoricalParameterValueDTO> categoricalParameterValues, List<Object> categories) {
    for (CategoricalParameterValueDTO parameterValue : categoricalParameterValues) {
      if (!categories.contains(parameterValue.getValue())) {
        return false;
      }
    }
    return true;
  }
}
