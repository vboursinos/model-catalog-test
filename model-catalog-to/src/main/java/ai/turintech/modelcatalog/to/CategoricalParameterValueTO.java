package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterValueTO extends AbstractUUIDIdentityTO {

  private static final long serialVersionUID = -3254308693794742605L;

  @NotNull(message = "must not be null")
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CategoricalParameterValueTO)) {
      return false;
    }

    CategoricalParameterValueTO categoricalParameterValueDTO = (CategoricalParameterValueTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), categoricalParameterValueDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "CategoricalParameterValueDTO{" + "id=" + getId() + ", value='" + getValue() + "'" + "}";
  }
}
