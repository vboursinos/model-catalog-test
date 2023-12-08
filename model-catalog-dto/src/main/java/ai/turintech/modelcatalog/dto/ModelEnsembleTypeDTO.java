package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

/** A DTO for the ModelEnsembleType entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelEnsembleTypeDTO extends AbstractUUIDIdentityDTO<UUID> {

  private UUID id;

  @NotNull(message = "must not be null")
  private String name;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

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
    if (!(o instanceof ModelEnsembleTypeDTO)) {
      return false;
    }

    ModelEnsembleTypeDTO modelEnsembleTypeDTO = (ModelEnsembleTypeDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, modelEnsembleTypeDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ModelEnsembleTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
