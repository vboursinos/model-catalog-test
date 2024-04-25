package database.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A Parameter. */
@Entity
@Table(name = "parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parameter {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "label", nullable = false)
  private String label;

  @Column(name = "description")
  private String description;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled;

  @Column(name = "fixed_value", nullable = false)
  private Boolean fixedValue;

  @Column(name = "ordering", nullable = false)
  private Integer ordering;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "parameter")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<ParameterTypeDefinition> definitions = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  private Model model;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public Parameter name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return this.label;
  }

  public Parameter label(String label) {
    this.setLabel(label);
    return this;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return this.description;
  }

  public Parameter description(String description) {
    this.setDescription(description);
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getEnabled() {
    return this.enabled;
  }

  public Parameter enabled(Boolean enabled) {
    this.setEnabled(enabled);
    return this;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getFixedValue() {
    return this.fixedValue;
  }

  public Parameter fixedValue(Boolean fixedValue) {
    this.setFixedValue(fixedValue);
    return this;
  }

  public void setFixedValue(Boolean fixedValue) {
    this.fixedValue = fixedValue;
  }

  public Integer getOrdering() {
    return this.ordering;
  }

  public Parameter ordering(Integer ordering) {
    this.setOrdering(ordering);
    return this;
  }

  public void setOrdering(Integer ordering) {
    this.ordering = ordering;
  }

  public Set<ParameterTypeDefinition> getDefinitions() {
    return this.definitions;
  }

  public void setDefinitions(Set<ParameterTypeDefinition> parameterTypeDefinitions) {
    if (this.definitions != null) {
      this.definitions.forEach(i -> i.setParameter(null));
    }
    if (parameterTypeDefinitions != null) {
      parameterTypeDefinitions.forEach(i -> i.setParameter(this));
    }
    this.definitions = parameterTypeDefinitions;
  }

  public Parameter definitions(Set<ParameterTypeDefinition> parameterTypeDefinitions) {
    this.setDefinitions(parameterTypeDefinitions);
    return this;
  }

  public Parameter addDefinitions(ParameterTypeDefinition parameterTypeDefinition) {
    this.definitions.add(parameterTypeDefinition);
    parameterTypeDefinition.setParameter(this);
    return this;
  }

  public Parameter removeDefinitions(ParameterTypeDefinition parameterTypeDefinition) {
    this.definitions.remove(parameterTypeDefinition);
    parameterTypeDefinition.setParameter(null);
    return this;
  }

  public Model getModel() {
    return this.model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public Parameter model(Model model) {
    this.setModel(model);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Parameter)) {
      return false;
    }
    return getId() != null && getId().equals(((Parameter) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "Parameter{"
        + "id="
        + getId()
        + ", name='"
        + getName()
        + "'"
        + ", label='"
        + getLabel()
        + "'"
        + ", description='"
        + getDescription()
        + "'"
        + ", enabled='"
        + getEnabled()
        + "'"
        + ", fixedValue='"
        + getFixedValue()
        + "'"
        + ", ordering="
        + getOrdering()
        + "}";
  }
}
