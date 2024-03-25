package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyGroupTypeDTO extends AbstractUUIDIdentityDTO {

  private static final long serialVersionUID = -536759035872405018L;

  @NotNull(message = "must not be null")
  private String name;

  private Set<DependencyTypeDTO> dependencyTypes = new HashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<DependencyTypeDTO> getDependencyTypes() {
    return this.dependencyTypes;
  }

  public void setDependencyTypes(Set<DependencyTypeDTO> dependencyTypes) {
    this.dependencyTypes = dependencyTypes;
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
    return "DependencyGroupTypeDTO{"
        + "name='"
        + name
        + '\''
        + ", dependencyTypes="
        + dependencyTypes
        + '}';
  }
}
