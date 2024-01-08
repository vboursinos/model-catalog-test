package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelEnsembleTypeTO extends AbstractUUIDIdentityTO implements Serializable {

  private static final long serialVersionUID = -8395745927703226088L;

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
    if (!(o instanceof ModelEnsembleTypeTO)) {
      return false;
    }

    ModelEnsembleTypeTO modelEnsembleTypeDTO = (ModelEnsembleTypeTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), modelEnsembleTypeDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ModelEnsembleTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
