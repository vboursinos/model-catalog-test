package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import java.util.*;

/** A DTO for the IntegerParameter entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterDTO extends AbstractUUIDIdentityDTO {

  private Integer defaultValue;

  private Set<IntegerParameterValueDTO> integerParameterValues = new HashSet<>();

  public Integer getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Integer defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Set<IntegerParameterValueDTO> getIntegerParameterValues() {
    return integerParameterValues;
  }

  public void setIntegerParameterValues(Set<IntegerParameterValueDTO> integerParameterValues) {
    this.integerParameterValues = integerParameterValues;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IntegerParameterDTO)) {
      return false;
    }

    IntegerParameterDTO integerParameterDTO = (IntegerParameterDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), integerParameterDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "IntegerParameterDTO{"
        + "parameterTypeDefinitionId="
        + getId()
        + ", defaultValue="
        + getDefaultValue()
        + "}";
  }
}
