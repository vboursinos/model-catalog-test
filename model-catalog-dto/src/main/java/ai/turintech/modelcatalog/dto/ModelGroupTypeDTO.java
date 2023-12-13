package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/** A DTO for the ModelGroupType entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelGroupTypeDTO extends AbstractUUIDIdentityDTO<UUID> {

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
    if (!(o instanceof ModelGroupTypeDTO)) {
      return false;
    }

    ModelGroupTypeDTO modelGroupTypeDTO = (ModelGroupTypeDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), modelGroupTypeDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ModelGroupTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
