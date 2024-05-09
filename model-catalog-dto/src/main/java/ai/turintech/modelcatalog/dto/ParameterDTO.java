package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import jakarta.validation.constraints.*;
import java.util.*;

/** A DTO for the Parameter entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterDTO extends AbstractUUIDIdentityDTO {
  @NotNull(message = "must not be null")
  private String name;

  @NotNull(message = "must not be null")
  private String label;

  private String description;

  @NotNull(message = "must not be null")
  private Boolean enabled;

  @NotNull(message = "must not be null")
  private Boolean fixedValue;

  @NotNull(message = "must not be null")
  private Integer ordering;

  @NotNull private UUID modelId;

  @NotNull private Set<ParameterTypeDefinitionDTO> definitions = new HashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getFixedValue() {
    return fixedValue;
  }

  public void setFixedValue(Boolean fixedValue) {
    this.fixedValue = fixedValue;
  }

  public Integer getOrdering() {
    return ordering;
  }

  public void setOrdering(Integer ordering) {
    this.ordering = ordering;
  }

  public UUID getModelId() {
    return modelId;
  }

  public void setModelId(UUID modelId) {
    this.modelId = modelId;
  }

  public Set<ParameterTypeDefinitionDTO> getDefinitions() {
    return definitions;
  }

  public void setDefinitions(Set<ParameterTypeDefinitionDTO> definitions) {
    this.definitions = definitions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParameterDTO)) {
      return false;
    }

    ParameterDTO parameterDTO = (ParameterDTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), parameterDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ParameterDTO{"
        + "id="
        + getId()
        + ", name='"
        + name
        + '\''
        + ", label='"
        + label
        + '\''
        + ", description='"
        + description
        + '\''
        + ", enabled="
        + enabled
        + ", fixedValue="
        + fixedValue
        + ", ordering="
        + ordering
        + ", modelId="
        + modelId
        + ", definitions="
        + definitions
        + '}';
  }
}
