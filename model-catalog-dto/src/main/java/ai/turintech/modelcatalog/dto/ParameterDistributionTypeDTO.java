package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.*;
import java.util.Objects;

/** A DTO for the ParameterDistributionType entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterDistributionTypeDTO extends AbstractUUIDIdentityDTO {

  @NotNull(message = "must not be null")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParameterDistributionTypeDTO)) {
      return false;
    }

    ParameterDistributionTypeDTO parameterDistributionTypeDTO = (ParameterDistributionTypeDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), parameterDistributionTypeDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ParameterDistributionTypeDTO{"
        + "id='"
        + getId()
        + "'"
        + ", name='"
        + getName()
        + "'"
        + "}";
  }
}
