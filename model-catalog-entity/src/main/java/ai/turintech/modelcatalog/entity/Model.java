package ai.turintech.modelcatalog.entity;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;

/**
 * A Model.
 */
@Table("model")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Model implements Serializable, Persistable<UUID> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "must not be null")
    @Column("name")
    private String name;

    @NotNull(message = "must not be null")
    @Column("display_name")
    private String displayName;

    @Column("description")
    private String description;

    @Column("advantages")
    private String advantages;

    @Column("disadvantages")
    private String disadvantages;

    @NotNull(message = "must not be null")
    @Column("enabled")
    private Boolean enabled;

    @NotNull(message = "must not be null")
    @Column("decistion_tree")
    private Boolean decistionTree;

    @Transient
    private boolean isPersisted;

    @Transient
    private Set<MlTaskType> mlTasks = new HashSet<>();

    @Transient
    private Set<ModelStructureType> structures = new HashSet<>();

    @Transient
    private Set<ModelType> types = new HashSet<>();

    @Transient
    private Set<ModelFamilyType> familyTypes = new HashSet<>();

    @Transient
    private Set<ModelEnsembleType> ensembleTypes = new HashSet<>();

    @Transient
    private Set<ModelGroupType> groups = new HashSet<>();

    @Transient
    private Set<Metric> incompatibleMetrics = new HashSet<>();

    @Transient
    private Parameter parameters;

    @Column("parameters_id")
    private UUID parametersId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Model id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Model name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Model displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public Model description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdvantages() {
        return this.advantages;
    }

    public Model advantages(String advantages) {
        this.setAdvantages(advantages);
        return this;
    }

    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    public String getDisadvantages() {
        return this.disadvantages;
    }

    public Model disadvantages(String disadvantages) {
        this.setDisadvantages(disadvantages);
        return this;
    }

    public void setDisadvantages(String disadvantages) {
        this.disadvantages = disadvantages;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Model enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getDecistionTree() {
        return this.decistionTree;
    }

    public Model decistionTree(Boolean decistionTree) {
        this.setDecistionTree(decistionTree);
        return this;
    }

    public void setDecistionTree(Boolean decistionTree) {
        this.decistionTree = decistionTree;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Model setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<MlTaskType> getMlTasks() {
        return this.mlTasks;
    }

    public void setMlTasks(Set<MlTaskType> mlTaskTypes) {
        if (this.mlTasks != null) {
            this.mlTasks.forEach(i -> i.setModels(null));
        }
        if (mlTaskTypes != null) {
            mlTaskTypes.forEach(i -> i.setModels(this));
        }
        this.mlTasks = mlTaskTypes;
    }

    public Model mlTasks(Set<MlTaskType> mlTaskTypes) {
        this.setMlTasks(mlTaskTypes);
        return this;
    }

    public Model addMlTask(MlTaskType mlTaskType) {
        this.mlTasks.add(mlTaskType);
        mlTaskType.setModels(this);
        return this;
    }

    public Model removeMlTask(MlTaskType mlTaskType) {
        this.mlTasks.remove(mlTaskType);
        mlTaskType.setModels(null);
        return this;
    }

    public Set<ModelStructureType> getStructures() {
        return this.structures;
    }

    public void setStructures(Set<ModelStructureType> modelStructureTypes) {
        if (this.structures != null) {
            this.structures.forEach(i -> i.setModel(null));
        }
        if (modelStructureTypes != null) {
            modelStructureTypes.forEach(i -> i.setModel(this));
        }
        this.structures = modelStructureTypes;
    }

    public Model structures(Set<ModelStructureType> modelStructureTypes) {
        this.setStructures(modelStructureTypes);
        return this;
    }

    public Model addStructure(ModelStructureType modelStructureType) {
        this.structures.add(modelStructureType);
        modelStructureType.setModel(this);
        return this;
    }

    public Model removeStructure(ModelStructureType modelStructureType) {
        this.structures.remove(modelStructureType);
        modelStructureType.setModel(null);
        return this;
    }

    public Set<ModelType> getTypes() {
        return this.types;
    }

    public void setTypes(Set<ModelType> modelTypes) {
        if (this.types != null) {
            this.types.forEach(i -> i.setModels(null));
        }
        if (modelTypes != null) {
            modelTypes.forEach(i -> i.setModels(this));
        }
        this.types = modelTypes;
    }

    public Model types(Set<ModelType> modelTypes) {
        this.setTypes(modelTypes);
        return this;
    }

    public Model addType(ModelType modelType) {
        this.types.add(modelType);
        modelType.setModels(this);
        return this;
    }

    public Model removeType(ModelType modelType) {
        this.types.remove(modelType);
        modelType.setModels(null);
        return this;
    }

    public Set<ModelFamilyType> getFamilyTypes() {
        return this.familyTypes;
    }

    public void setFamilyTypes(Set<ModelFamilyType> modelFamilyTypes) {
        if (this.familyTypes != null) {
            this.familyTypes.forEach(i -> i.setModels(null));
        }
        if (modelFamilyTypes != null) {
            modelFamilyTypes.forEach(i -> i.setModels(this));
        }
        this.familyTypes = modelFamilyTypes;
    }

    public Model familyTypes(Set<ModelFamilyType> modelFamilyTypes) {
        this.setFamilyTypes(modelFamilyTypes);
        return this;
    }

    public Model addFamilyType(ModelFamilyType modelFamilyType) {
        this.familyTypes.add(modelFamilyType);
        modelFamilyType.setModels(this);
        return this;
    }

    public Model removeFamilyType(ModelFamilyType modelFamilyType) {
        this.familyTypes.remove(modelFamilyType);
        modelFamilyType.setModels(null);
        return this;
    }

    public Set<ModelEnsembleType> getEnsembleTypes() {
        return this.ensembleTypes;
    }

    public void setEnsembleTypes(Set<ModelEnsembleType> modelEnsembleTypes) {
        if (this.ensembleTypes != null) {
            this.ensembleTypes.forEach(i -> i.setModels(null));
        }
        if (modelEnsembleTypes != null) {
            modelEnsembleTypes.forEach(i -> i.setModels(this));
        }
        this.ensembleTypes = modelEnsembleTypes;
    }

    public Model ensembleTypes(Set<ModelEnsembleType> modelEnsembleTypes) {
        this.setEnsembleTypes(modelEnsembleTypes);
        return this;
    }

    public Model addEnsembleType(ModelEnsembleType modelEnsembleType) {
        this.ensembleTypes.add(modelEnsembleType);
        modelEnsembleType.setModels(this);
        return this;
    }

    public Model removeEnsembleType(ModelEnsembleType modelEnsembleType) {
        this.ensembleTypes.remove(modelEnsembleType);
        modelEnsembleType.setModels(null);
        return this;
    }

    public Set<ModelGroupType> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<ModelGroupType> modelGroupTypes) {
        this.groups = modelGroupTypes;
    }

    public Model groups(Set<ModelGroupType> modelGroupTypes) {
        this.setGroups(modelGroupTypes);
        return this;
    }

    public Model addGroups(ModelGroupType modelGroupType) {
        this.groups.add(modelGroupType);
        modelGroupType.getModels().add(this);
        return this;
    }

    public Model removeGroups(ModelGroupType modelGroupType) {
        this.groups.remove(modelGroupType);
        modelGroupType.getModels().remove(this);
        return this;
    }

    public Set<Metric> getIncompatibleMetrics() {
        return this.incompatibleMetrics;
    }

    public void setIncompatibleMetrics(Set<Metric> metrics) {
        this.incompatibleMetrics = metrics;
    }

    public Model incompatibleMetrics(Set<Metric> metrics) {
        this.setIncompatibleMetrics(metrics);
        return this;
    }

    public Model addIncompatibleMetrics(Metric metric) {
        this.incompatibleMetrics.add(metric);
        metric.getModels().add(this);
        return this;
    }

    public Model removeIncompatibleMetrics(Metric metric) {
        this.incompatibleMetrics.remove(metric);
        metric.getModels().remove(this);
        return this;
    }

    public Parameter getParameters() {
        return this.parameters;
    }

    public void setParameters(Parameter parameter) {
        this.parameters = parameter;
        this.parametersId = parameter != null ? parameter.getId() : null;
    }

    public Model parameters(Parameter parameter) {
        this.setParameters(parameter);
        return this;
    }

    public UUID getParametersId() {
        return this.parametersId;
    }

    public void setParametersId(UUID parameter) {
        this.parametersId = parameter;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Model)) {
            return false;
        }
        return getId() != null && getId().equals(((Model) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Model{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", description='" + getDescription() + "'" +
            ", advantages='" + getAdvantages() + "'" +
            ", disadvantages='" + getDisadvantages() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", decistionTree='" + getDecistionTree() + "'" +
            "}";
    }
}
