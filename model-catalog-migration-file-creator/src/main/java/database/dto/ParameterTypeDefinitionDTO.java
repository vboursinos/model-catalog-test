package database.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;

/** A DTO for the ParameterTypeDefinition entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterTypeDefinitionDTO {

  private UUID id;
  private Integer ordering;

  private UUID distributionId;

  private UUID parameterId;

  private UUID typeId;

  private ParameterDistributionTypeDTO distribution;

  private CategoricalParameterDTO categoricalParameter;
  private BooleanParameterDTO booleanParameter;
  private FloatParameterDTO floatParameter;
  private IntegerParameterDTO integerParameter;

  //  private ParameterDTO parameter;

  private ParameterTypeDTO type;

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

  public ParameterDistributionTypeDTO getDistribution() {
    return distribution;
  }

  public void setDistribution(ParameterDistributionTypeDTO distribution) {
    this.distribution = distribution;
  }

  public ParameterTypeDTO getType() {
    return type;
  }

  public void setType(ParameterTypeDTO type) {
    this.type = type;
  }

  public CategoricalParameterDTO getCategoricalParameter() {
    return categoricalParameter;
  }

  public void setCategoricalParameter(CategoricalParameterDTO categoricalParameter) {
    this.categoricalParameter = categoricalParameter;
  }

  public BooleanParameterDTO getBooleanParameter() {
    return booleanParameter;
  }

  public void setBooleanParameter(BooleanParameterDTO booleanParameter) {
    this.booleanParameter = booleanParameter;
  }

  public FloatParameterDTO getFloatParameter() {
    return floatParameter;
  }

  public void setFloatParameter(FloatParameterDTO floatParameter) {
    this.floatParameter = floatParameter;
  }

  public IntegerParameterDTO getIntegerParameter() {
    return integerParameter;
  }

  public void setIntegerParameter(IntegerParameterDTO integerParameter) {
    this.integerParameter = integerParameter;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  //  public ParameterDTO getParameter() {
  //    return parameter;
  //  }
  //
  //  public void setParameter(ParameterDTO parameter) {
  //    this.parameter = parameter;
  //  }

  @Override
  public String toString() {
    return "ParameterTypeDefinitionDTO{" + "ordering='" + ordering + '\'' + '}';
  }
}
