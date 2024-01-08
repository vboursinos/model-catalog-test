package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A DTO for the FloatParameter entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterDTO extends AbstractUUIDIdentityDTO {
  private Double defaultValue;

  private List<FloatParameterRangeDTO> floatParameterRanges = new ArrayList<>();

  public Double getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Double defaultValue) {
    this.defaultValue = defaultValue;
  }

  public List<FloatParameterRangeDTO> getFloatParameterRanges() {
    return floatParameterRanges;
  }

  public void setFloatParameterRanges(List<FloatParameterRangeDTO> floatParameterRanges) {
    this.floatParameterRanges = floatParameterRanges;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FloatParameterDTO)) {
      return false;
    }

    FloatParameterDTO floatParameterDTO = (FloatParameterDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), floatParameterDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "FloatParameterDTO{"
        + "parameterTypeDefinitionId="
        + getId()
        + ", defaultValue="
        + getDefaultValue()
        + "}";
  }
}
