package ai.turintech.modelcatalog.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelTO implements Serializable {

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

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Set<ModelGroupTypeTO> groups = new HashSet<>();

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Set<MetricTO> incompatibleMetrics = new HashSet<>();
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<ParameterTO> parameters = new ArrayList<>();
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private MlTaskTypeTO mlTask;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private ModelStructureTypeTO structure;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private ModelTypeTO type;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private ModelFamilyTypeTO familyType;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private ModelEnsembleTypeTO ensembleType;
    private String taskName;

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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
        return "ModelDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", advantages='" + advantages + '\'' +
                ", disadvantages='" + disadvantages + '\'' +
                ", enabled=" + enabled +
                ", decisionTree=" + decisionTree +
                ", groups=" + groups +
                ", incompatibleMetrics=" + incompatibleMetrics +
                ", parameters=" + parameters +
                ", mlTask=" + mlTask +
                ", structure=" + structure +
                ", type=" + type +
                ", familyType=" + familyType +
                ", ensembleType=" + ensembleType +
                '}';
    }
}
