package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractDTO;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.*;

/** A DTO for the Model entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelDTO extends AbstractDTO implements Serializable {

  private UUID id;

  @NotNull(message = "must not be null")
  private String name;

  @NotNull(message = "must not be null")
  private String displayName;

  private String description;

  private String[] advantages;

  private String[] disadvantages;

  @NotNull(message = "must not be null")
  private Boolean enabled;

  @NotNull(message = "must not be null")
  private Boolean decisionTree;

  private Set<ModelGroupTypeDTO> groups = new HashSet<>();

  private Set<MetricDTO> incompatibleMetrics = new HashSet<>();

  private List<ParameterDTO> parameters = new ArrayList<>();

  private MlTaskTypeDTO mlTask;

  private ModelStructureTypeDTO structure;

  private ModelTypeDTO type;

  private ModelFamilyTypeDTO familyType;

  private ModelEnsembleTypeDTO ensembleType;

  public ModelDTO(
      UUID id,
      String name,
      String displayName,
      String description,
      String[] advantages,
      String[] disadvantages,
      Boolean enabled,
      Boolean decisionTree) {
    this.id = id;
    this.name = name;
    this.displayName = displayName;
    this.description = description;
    this.advantages = advantages;
    this.disadvantages = disadvantages;
    this.enabled = enabled;
    this.decisionTree = decisionTree;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String[] getAdvantages() {
    return advantages;
  }

  public void setAdvantages(String[] advantages) {
    this.advantages = advantages;
  }

  public String[] getDisadvantages() {
    return disadvantages;
  }

  public void setDisadvantages(String[] disadvantages) {
    this.disadvantages = disadvantages;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getDecisionTree() {
    return decisionTree;
  }

  public void setDecisionTree(Boolean decisionTree) {
    this.decisionTree = decisionTree;
  }

  public Set<ModelGroupTypeDTO> getGroups() {
    return groups;
  }

  public void setGroups(Set<ModelGroupTypeDTO> groups) {
    this.groups = groups;
  }

  public Set<MetricDTO> getIncompatibleMetrics() {
    return incompatibleMetrics;
  }

  public void setIncompatibleMetrics(Set<MetricDTO> incompatibleMetrics) {
    this.incompatibleMetrics = incompatibleMetrics;
  }

  public MlTaskTypeDTO getMlTask() {
    return mlTask;
  }

  public void setMlTask(MlTaskTypeDTO mlTask) {
    this.mlTask = mlTask;
  }

  public ModelStructureTypeDTO getStructure() {
    return structure;
  }

  public void setStructure(ModelStructureTypeDTO structure) {
    this.structure = structure;
  }

  public ModelTypeDTO getType() {
    return type;
  }

  public void setType(ModelTypeDTO type) {
    this.type = type;
  }

  public ModelFamilyTypeDTO getFamilyType() {
    return familyType;
  }

  public void setFamilyType(ModelFamilyTypeDTO familyType) {
    this.familyType = familyType;
  }

  public ModelEnsembleTypeDTO getEnsembleType() {
    return ensembleType;
  }

  public void setEnsembleType(ModelEnsembleTypeDTO ensembleType) {
    this.ensembleType = ensembleType;
  }

  public List<ParameterDTO> getParameters() {
    return parameters;
  }

  public void setParameters(List<ParameterDTO> parameters) {
    this.parameters = parameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelDTO)) {
      return false;
    }

    ModelDTO modelDTO = (ModelDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, modelDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore

  @Override
  public String toString() {
    return "ModelDTO{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", displayName='"
        + displayName
        + '\''
        + ", description='"
        + description
        + '\''
        + ", advantages='"
        + advantages
        + '\''
        + ", disadvantages='"
        + disadvantages
        + '\''
        + ", enabled="
        + enabled
        + ", decisionTree="
        + decisionTree
        + ", groups="
        + groups
        + ", incompatibleMetrics="
        + incompatibleMetrics
        + ", parameters="
        + parameters
        + ", mlTask="
        + mlTask
        + ", structure="
        + structure
        + ", type="
        + type
        + ", familyType="
        + familyType
        + ", ensembleType="
        + ensembleType
        + '\''
        + '}';
  }
}
