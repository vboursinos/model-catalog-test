package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/** A DTO for the ParameterType entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterTypeDTO extends AbstractUUIDIdentityDTO<UUID> {

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
    if (!(o instanceof ParameterTypeDTO)) {
      return false;
    }

    ParameterTypeDTO parameterTypeDTO = (ParameterTypeDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), parameterTypeDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ParameterTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
