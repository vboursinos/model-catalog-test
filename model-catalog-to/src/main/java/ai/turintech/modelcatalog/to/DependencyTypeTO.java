package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyTypeTO extends AbstractUUIDIdentityTO {

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
    if (!(o instanceof DependencyTypeTO)) {
      return false;
    }

    DependencyTypeTO dependencyGroupTypeTO = (DependencyTypeTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), dependencyGroupTypeTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  @Override
  public String toString() {
    return "DependencyTypeTO{" + "name='" + name + '}';
  }
}
