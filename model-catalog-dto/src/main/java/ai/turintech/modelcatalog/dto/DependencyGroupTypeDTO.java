package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyGroupTypeDTO extends AbstractUUIDIdentityDTO implements Serializable {

  private static final long serialVersionUID = -536759035872405018L;

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
    if (!(o instanceof DependencyGroupTypeDTO)) {
      return false;
    }

    DependencyGroupTypeDTO dependencyGroupTypeDTO = (DependencyGroupTypeDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), dependencyGroupTypeDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore

  @Override
  public String toString() {
    return "DependencyGroupTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
