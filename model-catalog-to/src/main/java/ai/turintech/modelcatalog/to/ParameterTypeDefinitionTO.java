package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterTypeDefinitionTO extends AbstractUUIDIdentityTO<UUID>
    implements Serializable {

  private static final long serialVersionUID = 5585810515555669383L;

  @NotNull(message = "must not be null")
  private Integer ordering;

  @NotNull(message = "must not be null")
  private UUID distributionId;

  @NotNull(message = "must not be null")
  private UUID parameterId;

  @NotNull(message = "must not be null")
  private UUID typeId;

  private ParameterDistributionTypeTO distribution;

  private CategoricalParameterTO categoricalParameter;
  private BooleanParameterTO booleanParameter;
  private FloatParameterTO floatParameter;
  private IntegerParameterTO integerParameter;

  //    private ParameterDTO parameter;
  //
  private ParameterTypeTO type;

  public Integer getOrdering() {
    return ordering;
  }

  public void setOrdering(Integer ordering) {
    this.ordering = ordering;
  }

  public UUID getDistributionId() {
    return distributionId;
  }

  public void setDistributionId(UUID distributionId) {
    this.distributionId = distributionId;
  }

  public UUID getParameterId() {
    return parameterId;
  }

  public void setParameterId(UUID parameterId) {
    this.parameterId = parameterId;
  }

  public UUID getTypeId() {
    return typeId;
  }

  public void setTypeId(UUID typeId) {
    this.typeId = typeId;
  }

  public ParameterDistributionTypeTO getDistribution() {
    return distribution;
  }

  public void setDistribution(ParameterDistributionTypeTO distribution) {
    this.distribution = distribution;
  }

  public ParameterTypeTO getType() {
    return type;
  }

  public void setType(ParameterTypeTO type) {
    this.type = type;
  }

  public CategoricalParameterTO getCategoricalParameter() {
    return categoricalParameter;
  }

  public void setCategoricalParameter(CategoricalParameterTO categoricalParameter) {
    this.categoricalParameter = categoricalParameter;
  }

  public BooleanParameterTO getBooleanParameter() {
    return booleanParameter;
  }

  public void setBooleanParameter(BooleanParameterTO booleanParameter) {
    this.booleanParameter = booleanParameter;
  }

  public FloatParameterTO getFloatParameter() {
    return floatParameter;
  }

  public void setFloatParameter(FloatParameterTO floatParameter) {
    this.floatParameter = floatParameter;
  }

  public IntegerParameterTO getIntegerParameter() {
    return integerParameter;
  }

  public void setIntegerParameter(IntegerParameterTO integerParameter) {
    this.integerParameter = integerParameter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParameterTypeDefinitionTO)) {
      return false;
    }

    ParameterTypeDefinitionTO parameterTypeDefinitionDTO = (ParameterTypeDefinitionTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), parameterTypeDefinitionDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore

  @Override
  public String toString() {
    return "ParameterTypeDefinitionDTO{"
        + "id="
        + getId()
        + ", ordering="
        + ordering
        + ", distributionId="
        + distributionId
        + ", parameterId="
        + parameterId
        + ", typeId="
        + typeId
        + ", distribution="
        + distribution
        + ", type="
        + type
        + '}';
  }
}
