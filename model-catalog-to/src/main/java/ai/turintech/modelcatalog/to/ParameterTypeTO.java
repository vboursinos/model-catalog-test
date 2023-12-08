package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterTypeTO extends AbstractUUIDIdentityTO<UUID> implements Serializable {

  private static final long serialVersionUID = 5395410671139921508L;

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
    if (!(o instanceof ParameterTypeTO)) {
      return false;
    }

    ParameterTypeTO parameterTypeDTO = (ParameterTypeTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, parameterTypeDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ParameterTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
