package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import jakarta.validation.constraints.NotNull;
import java.util.*;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelTO extends AbstractUUIDIdentityTO {

  private static final long serialVersionUID = 1294639702776637323L;

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

  private Set<ModelGroupTypeTO> groups = new HashSet<>();

  private Set<MetricTO> incompatibleMetrics = new HashSet<>();

  private List<ParameterTO> parameters = new ArrayList<>();

  private MlTaskTypeTO mlTask;

  private ModelStructureTypeTO structure;

  private ModelTypeTO type;

  private ModelFamilyTypeTO familyType;

  private ModelEnsembleTypeTO ensembleType;

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

  public Set<ModelGroupTypeTO> getGroups() {
    return groups;
  }

  public void setGroups(Set<ModelGroupTypeTO> groups) {
    this.groups = groups;
  }

  public Set<MetricTO> getIncompatibleMetrics() {
    return incompatibleMetrics;
  }

  public void setIncompatibleMetrics(Set<MetricTO> incompatibleMetrics) {
    this.incompatibleMetrics = incompatibleMetrics;
  }

  public MlTaskTypeTO getMlTask() {
    return mlTask;
  }

  public void setMlTask(MlTaskTypeTO mlTask) {
    this.mlTask = mlTask;
  }

  public ModelStructureTypeTO getStructure() {
    return structure;
  }

  public void setStructure(ModelStructureTypeTO structure) {
    this.structure = structure;
  }

  public ModelTypeTO getType() {
    return type;
  }

  public void setType(ModelTypeTO type) {
    this.type = type;
  }

  public ModelFamilyTypeTO getFamilyType() {
    return familyType;
  }

  public void setFamilyType(ModelFamilyTypeTO familyType) {
    this.familyType = familyType;
  }

  public ModelEnsembleTypeTO getEnsembleType() {
    return ensembleType;
  }

  public void setEnsembleType(ModelEnsembleTypeTO ensembleType) {
    this.ensembleType = ensembleType;
  }

  public List<ParameterTO> getParameters() {
    return parameters;
  }

  public void setParameters(List<ParameterTO> parameters) {
    this.parameters = parameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelTO)) {
      return false;
    }

    ModelTO modelDTO = (ModelTO) o;
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), modelDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore

  @Override
  public String toString() {
    return "ModelDTO{"
        + "id="
        + getId()
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
        + '}';
  }
}
