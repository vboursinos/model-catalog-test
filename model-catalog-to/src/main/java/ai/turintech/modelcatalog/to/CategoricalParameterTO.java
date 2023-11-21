package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractTO;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterTO extends AbstractTO implements Serializable {

  private static final long serialVersionUID = 6418488670699594296L;

  private UUID parameterTypeDefinitionId;

  private String defaultValue;

  private Set<CategoricalParameterValueTO> categoricalParameterValues = new HashSet<>();;

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public UUID getParameterTypeDefinitionId() {
    return parameterTypeDefinitionId;
  }

  public void setParameterTypeDefinitionId(UUID parameterTypeDefinitionId) {
    this.parameterTypeDefinitionId = parameterTypeDefinitionId;
  }

  public Set<CategoricalParameterValueTO> getCategoricalParameterValues() {
    return categoricalParameterValues;
  }

  public void setCategoricalParameterValues(
      Set<CategoricalParameterValueTO> categoricalParameterValues) {
    this.categoricalParameterValues = categoricalParameterValues;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CategoricalParameterTO)) {
      return false;
    }

    CategoricalParameterTO categoricalParameterDTO = (CategoricalParameterTO) o;
    if (this.parameterTypeDefinitionId == null) {
      return false;
    }
    return Objects.equals(
        this.parameterTypeDefinitionId, categoricalParameterDTO.parameterTypeDefinitionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.parameterTypeDefinitionId);
  }

  @Override
  public String toString() {
    return "CategoricalParameterDTO{"
        + "parameterTypeDefinitionId="
        + parameterTypeDefinitionId
        + ", defaultValue='"
        + defaultValue
        + '\''
        + '}';
  }
}
