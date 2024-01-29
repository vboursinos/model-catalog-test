package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/** A Model. */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Model extends AbstractUUIDIdentityEntity {

  private static final long serialVersionUID = 1L;

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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private List<Parameter> parameters = new ArrayList<>();

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
  @JoinColumn(name = "dependency_group_id", referencedColumnName = "id")
  private DependencyGroupType dependencyGroupType;

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

  public String[] getAdvantages() {
    return this.advantages;
  }

  public void setAdvantages(String[] advantages) {
    this.advantages = advantages;
  }

  public String[] getDisadvantages() {
    return this.disadvantages;
  }

  public void setDisadvantages(String[] disadvantages) {
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

  public Boolean getDecisionTree() {
    return this.decisionTree;
  }

  public Model decisionTree(Boolean decisionTree) {
    this.setDecisionTree(decisionTree);
    return this;
  }

  public void setDecisionTree(Boolean decisionTree) {
    this.decisionTree = decisionTree;
  }

  public List<Parameter> getParameters() {
    return this.parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    if (this.parameters != null) {
      this.parameters.forEach(i -> i.setModel(null));
    }
    if (parameters != null) {
      parameters.forEach(i -> i.setModel(this));
    }
    this.parameters = parameters;
  }

  public Model parameters(List<Parameter> parameters) {
    this.setParameters(parameters);
    return this;
  }

  public Model addParameters(Parameter parameter) {
    this.parameters.add(parameter);
    parameter.setModel(this);
    return this;
  }

  public Model removeParameters(Parameter parameter) {
    this.parameters.remove(parameter);
    parameter.setModel(null);
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
    return this;
  }

  public Model removeGroups(ModelGroupType modelGroupType) {
    this.groups.remove(modelGroupType);
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
    return this;
  }

  public Model removeIncompatibleMetrics(Metric metric) {
    this.incompatibleMetrics.remove(metric);
    return this;
  }

  public MlTaskType getMlTask() {
    return this.mlTask;
  }

  public void setMlTask(MlTaskType mlTaskType) {
    this.mlTask = mlTaskType;
  }

  public Model mlTask(MlTaskType mlTaskType) {
    this.setMlTask(mlTaskType);
    return this;
  }

  public ModelStructureType getStructure() {
    return this.structure;
  }

  public void setStructure(ModelStructureType modelStructureType) {
    this.structure = modelStructureType;
  }

  public Model structure(ModelStructureType modelStructureType) {
    this.setStructure(modelStructureType);
    return this;
  }

  public ModelType getType() {
    return this.type;
  }

  public void setType(ModelType modelType) {
    this.type = modelType;
  }

  public Model type(ModelType modelType) {
    this.setType(modelType);
    return this;
  }

  public ModelFamilyType getFamilyType() {
    return this.familyType;
  }

  public void setFamilyType(ModelFamilyType modelFamilyType) {
    this.familyType = modelFamilyType;
  }

  public Model familyType(ModelFamilyType modelFamilyType) {
    this.setFamilyType(modelFamilyType);
    return this;
  }

  public ModelEnsembleType getEnsembleType() {
    return this.ensembleType;
  }

  public void setEnsembleType(ModelEnsembleType modelEnsembleType) {
    this.ensembleType = modelEnsembleType;
  }

  public Model ensembleType(ModelEnsembleType modelEnsembleType) {
    this.setEnsembleType(modelEnsembleType);
    return this;
  }

  public DependencyGroupType getDependencyGroupType() {
    return this.dependencyGroupType;
  }

  public void setDependencyGroupType(DependencyGroupType dependencyGroupType) {
    this.dependencyGroupType = dependencyGroupType;
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
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore

  @Override
  public String toString() {
    return "Model{"
        + "name='"
        + name
        + '\''
        + ", displayName='"
        + displayName
        + '\''
        + ", description='"
        + description
        + '\''
        + ", advantages="
        + Arrays.toString(advantages)
        + ", disadvantages="
        + Arrays.toString(disadvantages)
        + ", enabled="
        + enabled
        + ", decisionTree="
        + decisionTree
        + ", parameters="
        + parameters
        + ", groups="
        + groups
        + ", incompatibleMetrics="
        + incompatibleMetrics
        + ", mlTask="
        + mlTask
        + ", structure="
        + structure
        + ", dependencyGroupType="
        + dependencyGroupType
        + ", type="
        + type
        + ", familyType="
        + familyType
        + ", ensembleType="
        + ensembleType
        + '}';
  }
}
