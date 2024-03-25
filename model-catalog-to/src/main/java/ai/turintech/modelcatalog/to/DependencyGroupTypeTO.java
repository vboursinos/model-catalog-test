package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyGroupTypeTO extends AbstractUUIDIdentityTO {

  private static final long serialVersionUID = -536759035872405018L;

  @NotNull(message = "must not be null")
  private String name;

  private Set<DependencyTypeTO> dependencyTypes = new HashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<DependencyTypeTO> getDependencyTypes() {
    return dependencyTypes;
  }

  public void setDependencyTypes(Set<DependencyTypeTO> dependencyTypes) {
    this.dependencyTypes = dependencyTypes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DependencyGroupTypeTO)) {
      return false;
    }

    DependencyGroupTypeTO dependencyGroupTypeTO = (DependencyGroupTypeTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), dependencyGroupTypeTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "DependencyGroupTypeTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
