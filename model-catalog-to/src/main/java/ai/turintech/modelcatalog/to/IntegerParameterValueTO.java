package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterValueTO extends AbstractUUIDIdentityTO implements Serializable {

  private static final long serialVersionUID = 4737079565447734718L;

  @NotNull(message = "must not be null")
  private Integer lower;

  @NotNull(message = "must not be null")
  private Integer upper;

  public Integer getLower() {
    return lower;
  }

  public void setLower(Integer lower) {
    this.lower = lower;
  }

  public Integer getUpper() {
    return upper;
  }

  public void setUpper(Integer upper) {
    this.upper = upper;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "IntegerParameterValueDTO{"
        + "id="
        + getId()
        + ", lower="
        + getLower()
        + ", upper="
        + getUpper()
        + "}";
  }
}
