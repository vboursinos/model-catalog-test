package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterDistributionTypeTO extends AbstractUUIDIdentityTO {

  private static final long serialVersionUID = -8169187641592512395L;

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
    if (!(o instanceof ParameterDistributionTypeTO)) {
      return false;
    }

    ParameterDistributionTypeTO parameterDistributionTypeDTO = (ParameterDistributionTypeTO) o;
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
