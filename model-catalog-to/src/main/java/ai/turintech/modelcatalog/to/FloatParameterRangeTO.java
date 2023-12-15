package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterRangeTO extends AbstractUUIDIdentityTO<UUID> implements Serializable {

  private static final long serialVersionUID = -806224531073871956L;

  @NotNull(message = "must not be null")
  private Boolean isLeftOpen;

  @NotNull(message = "must not be null")
  private Boolean isRightOpen;

  @NotNull(message = "must not be null")
  private Double lower;

  @NotNull(message = "must not be null")
  private Double upper;

  public Boolean getIsLeftOpen() {
    return isLeftOpen;
  }

  public void setIsLeftOpen(Boolean isLeftOpen) {
    this.isLeftOpen = isLeftOpen;
  }

  public Boolean getIsRightOpen() {
    return isRightOpen;
  }

  public void setIsRightOpen(Boolean isRightOpen) {
    this.isRightOpen = isRightOpen;
  }

  public Double getLower() {
    return lower;
  }

  public void setLower(Double lower) {
    this.lower = lower;
  }

  public Double getUpper() {
    return upper;
  }

  public void setUpper(Double upper) {
    this.upper = upper;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FloatParameterRangeTO)) {
      return false;
    }

    FloatParameterRangeTO floatParameterRangeDTO = (FloatParameterRangeTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), floatParameterRangeDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "FloatParameterRangeDTO{"
        + "id="
        + getId()
        + ", isLeftOpen='"
        + getIsLeftOpen()
        + "'"
        + ", isRightOpen='"
        + getIsRightOpen()
        + "'"
        + ", lower="
        + getLower()
        + ", upper="
        + getUpper()
        + "}";
  }
}
