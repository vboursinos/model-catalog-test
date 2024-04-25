package database.entity;

import jakarta.persistence.*;
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
public class IntegerParameter extends BaseTypeParameter {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "default_value")
  private Integer defaultValue;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "integerParameter")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<IntegerParameterValue> integerParameterValues = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public IntegerParameter() {}

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

  @Override
  public String toString() {
    return "IntegerParameter{" + "defaultValue=" + defaultValue + '}';
  }
}
