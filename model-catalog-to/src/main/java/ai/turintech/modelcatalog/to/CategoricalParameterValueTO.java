package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterValueTO extends AbstractTO implements Serializable {

  private static final long serialVersionUID = -3254308693794742605L;
  private UUID id;

  @NotNull(message = "must not be null")
  private String value;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

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
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, categoricalParameterValueDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "CategoricalParameterValueDTO{" + "id=" + getId() + ", value='" + getValue() + "'" + "}";
  }
}
