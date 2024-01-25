package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.*;
import java.util.Objects;

/** A DTO for the CategoricalParameterValue entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterValueDTO extends AbstractUUIDIdentityDTO {

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
    if (!(o instanceof CategoricalParameterValueDTO)) {
      return false;
    }

    CategoricalParameterValueDTO categoricalParameterValueDTO = (CategoricalParameterValueDTO) o;
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
