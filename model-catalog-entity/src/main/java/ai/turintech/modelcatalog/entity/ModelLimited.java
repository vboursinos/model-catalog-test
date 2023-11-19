package ai.turintech.modelcatalog.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/** A Model. */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelLimited implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull
  @Column(name = "display_name", nullable = false)
  private String displayName;

  @Column(name = "description")
  private String description;

  //    @Column(name = "advantages")
  @Type(StringArrayType.class)
  @Column(name = "advantages", columnDefinition = "text[]")
  private String[] advantages;

  //    @Column(name = "disadvantages")
  @Type(StringArrayType.class)
  @Column(name = "disadvantages", columnDefinition = "text[]")
  private String[] disadvantages;

  @NotNull
  @Column(name = "enabled", nullable = false)
  private Boolean enabled;

  @NotNull
  @Column(name = "decision_tree", nullable = false)
  private Boolean decisionTree;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "rel_model__groups",
      joinColumns = @JoinColumn(name = "model_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<ModelGroupType> groups = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "rel_model__incompatible_metrics",
      joinColumns = @JoinColumn(name = "model_id"),
      inverseJoinColumns = @JoinColumn(name = "metric_id"))
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Metric> incompatibleMetrics = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ml_task_id", referencedColumnName = "id")
  private MlTaskType mlTask;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "structure_id", referencedColumnName = "id")
  private ModelStructureType structure;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "model_type_id", referencedColumnName = "id")
  private ModelType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "family_type_id", referencedColumnName = "id")
  private ModelFamilyType familyType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ensemble_type_id", referencedColumnName = "id")
  private ModelEnsembleType ensembleType;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public UUID getId() {
    return this.id;
  }

  public ModelLimited id(UUID id) {
    this.setId(id);
    return this;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public ModelLimited name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public ModelLimited displayName(String displayName) {
    this.setDisplayName(displayName);
    return this;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return this.description;
  }

  public ModelLimited description(String description) {
    this.setDescription(description);
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String[] getAdvantages() {
    return this.advantages;
  }

  //    public Model advantages(String advantages) {
  //        this.setAdvantages(advantages);
  //        return this;
  //    }

  public void setAdvantages(String[] advantages) {
    this.advantages = advantages;
  }

  public String[] getDisadvantages() {
    return this.disadvantages;
  }

  //    public Model disadvantages(String disadvantages) {
  //        this.setDisadvantages(disadvantages);
  //        return this;
  //    }

  public void setDisadvantages(String[] disadvantages) {
    this.disadvantages = disadvantages;
  }

  public Boolean getEnabled() {
    return this.enabled;
  }

  public ModelLimited enabled(Boolean enabled) {
    this.setEnabled(enabled);
    return this;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getDecisionTree() {
    return this.decisionTree;
  }

  public ModelLimited decisionTree(Boolean decisionTree) {
    this.setDecisionTree(decisionTree);
    return this;
  }

  public void setDecisionTree(Boolean decisionTree) {
    this.decisionTree = decisionTree;
  }

  public Set<ModelGroupType> getGroups() {
    return this.groups;
  }

  public void setGroups(Set<ModelGroupType> modelGroupTypes) {
    this.groups = modelGroupTypes;
  }

  public ModelLimited groups(Set<ModelGroupType> modelGroupTypes) {
    this.setGroups(modelGroupTypes);
    return this;
  }

  public ModelLimited addGroups(ModelGroupType modelGroupType) {
    this.groups.add(modelGroupType);
    return this;
  }

  public ModelLimited removeGroups(ModelGroupType modelGroupType) {
    this.groups.remove(modelGroupType);
    return this;
  }

  public Set<Metric> getIncompatibleMetrics() {
    return this.incompatibleMetrics;
  }

  public void setIncompatibleMetrics(Set<Metric> metrics) {
    this.incompatibleMetrics = metrics;
  }

  public ModelLimited incompatibleMetrics(Set<Metric> metrics) {
    this.setIncompatibleMetrics(metrics);
    return this;
  }

  public ModelLimited addIncompatibleMetrics(Metric metric) {
    this.incompatibleMetrics.add(metric);
    return this;
  }

  public ModelLimited removeIncompatibleMetrics(Metric metric) {
    this.incompatibleMetrics.remove(metric);
    return this;
  }

  public MlTaskType getMlTask() {
    return this.mlTask;
  }

  public void setMlTask(MlTaskType mlTaskType) {
    this.mlTask = mlTaskType;
  }

  public ModelLimited mlTask(MlTaskType mlTaskType) {
    this.setMlTask(mlTaskType);
    return this;
  }

  public ModelStructureType getStructure() {
    return this.structure;
  }

  public void setStructure(ModelStructureType modelStructureType) {
    this.structure = modelStructureType;
  }

  public ModelLimited structure(ModelStructureType modelStructureType) {
    this.setStructure(modelStructureType);
    return this;
  }

  public ModelType getType() {
    return this.type;
  }

  public void setType(ModelType modelType) {
    this.type = modelType;
  }

  public ModelLimited type(ModelType modelType) {
    this.setType(modelType);
    return this;
  }

  public ModelFamilyType getFamilyType() {
    return this.familyType;
  }

  public void setFamilyType(ModelFamilyType modelFamilyType) {
    this.familyType = modelFamilyType;
  }

  public ModelLimited familyType(ModelFamilyType modelFamilyType) {
    this.setFamilyType(modelFamilyType);
    return this;
  }

  public ModelEnsembleType getEnsembleType() {
    return this.ensembleType;
  }

  public void setEnsembleType(ModelEnsembleType modelEnsembleType) {
    this.ensembleType = modelEnsembleType;
  }

  public ModelLimited ensembleType(ModelEnsembleType modelEnsembleType) {
    this.setEnsembleType(modelEnsembleType);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelLimited)) {
      return false;
    }
    return getId() != null && getId().equals(((ModelLimited) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "Model{"
        + "id="
        + getId()
        + ", name='"
        + getName()
        + "'"
        + ", displayName='"
        + getDisplayName()
        + "'"
        + ", description='"
        + getDescription()
        + "'"
        + ", advantages='"
        + getAdvantages()
        + "'"
        + ", disadvantages='"
        + getDisadvantages()
        + "'"
        + ", enabled='"
        + getEnabled()
        + "'"
        + ", decisionTree='"
        + getDecisionTree()
        + "'"
        + "}";
  }
}
