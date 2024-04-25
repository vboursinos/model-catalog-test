package database.dto;

import java.util.*;

/** A DTO for the Parameter entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterDTO {

  private UUID id;
  private String name;

  private String label;

  private String description;

  private Boolean enabled;

  private Boolean fixedValue;

  private Integer ordering;

  private UUID modelId;

  //  private ModelDTO model;

  private Set<ParameterTypeDefinitionDTO> definitions = new HashSet<>();

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

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  //  public ModelDTO getModel() {
  //    return model;
  //  }
  //
  //  public void setModel(ModelDTO model) {
  //    this.model = model;
  //  }

  @Override
  public String toString() {
    return "ParameterDTO{" + "name='" + name + '\'' + '}';
  }
}
