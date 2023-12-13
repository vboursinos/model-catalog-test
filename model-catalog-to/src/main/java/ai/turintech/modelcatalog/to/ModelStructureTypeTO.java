package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelStructureTypeTO extends AbstractUUIDIdentityTO<UUID> implements Serializable {

  private static final long serialVersionUID = -1404025172100088206L;

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
    if (!(o instanceof ModelStructureTypeTO)) {
      return false;
    }

    ModelStructureTypeTO modelStructureTypeDTO = (ModelStructureTypeTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), modelStructureTypeDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ModelStructureTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
