package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A IntegerParameter. */
@Entity
@Table(name = "integer_parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameter extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "parameter_type_definition_id", insertable = false, updatable = false)
  private UUID parameterTypeDefinitionId;

  @Column(name = "default_value")
  private Integer defaultValue;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parameter_type_definition_id", unique = true)
  private ParameterTypeDefinition parameterTypeDefinition;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "integerParameter")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<IntegerParameterValue> integerParameterValues = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public IntegerParameter() {}

  public IntegerParameter(ParameterTypeDefinition parameterTypeDefinition) {
    this.parameterTypeDefinitionId = parameterTypeDefinition.getId();
    this.parameterTypeDefinition = parameterTypeDefinition;
  }

  public UUID getParameterTypeDefinitionId() {
    return parameterTypeDefinitionId;
  }

  public void setParameterTypeDefinitionId(UUID parameterTypeDefinitionId) {
    this.parameterTypeDefinitionId = parameterTypeDefinitionId;
  }

  public Integer getDefaultValue() {
    return this.defaultValue;
  }

  public IntegerParameter defaultValue(Integer defaultValue) {
    this.setDefaultValue(defaultValue);
    return this;
  }

  public void setDefaultValue(Integer defaultValue) {
    this.defaultValue = defaultValue;
  }

  public ParameterTypeDefinition getParameterTypeDefinition() {
    return this.parameterTypeDefinition;
  }

  public void setParameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.parameterTypeDefinition = parameterTypeDefinition;
  }

  public IntegerParameter parameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.setParameterTypeDefinition(parameterTypeDefinition);
    return this;
  }

  public Set<IntegerParameterValue> getIntegerParameterValues() {
    return this.integerParameterValues;
  }

  public void setIntegerParameterValues(Set<IntegerParameterValue> integerParameterValues) {
    if (this.integerParameterValues != null) {
      this.integerParameterValues.forEach(i -> i.setIntegerParameter(null));
    }
    if (integerParameterValues != null) {
      integerParameterValues.forEach(i -> i.setIntegerParameter(this));
    }
    this.integerParameterValues = integerParameterValues;
  }

  public IntegerParameter integerParameterValues(
      Set<IntegerParameterValue> integerParameterValues) {
    this.setIntegerParameterValues(integerParameterValues);
    return this;
  }

  public IntegerParameter addIntegerParameterValue(IntegerParameterValue integerParameterValue) {
    this.integerParameterValues.add(integerParameterValue);
    integerParameterValue.setIntegerParameter(this);
    return this;
  }

  public IntegerParameter removeIntegerParameterValue(IntegerParameterValue integerParameterValue) {
    this.integerParameterValues.remove(integerParameterValue);
    integerParameterValue.setIntegerParameter(null);
    return this;
  }

  // prettier-ignore

  @Override
  public String toString() {
    return "IntegerParameter{"
        + "defaultValue="
        + defaultValue
        + ", parameterTypeDefinition="
        + parameterTypeDefinition
        + '}';
  }
}
