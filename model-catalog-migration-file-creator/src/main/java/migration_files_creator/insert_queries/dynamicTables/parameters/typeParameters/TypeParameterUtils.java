package migration_files_creator.insert_queries.dynamicTables.parameters.typeParameters;

import database.dto.CategoricalParameterValueDTO;
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
