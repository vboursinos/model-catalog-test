package ai.turintech.modelcatalog.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A BooleanParameter. */
@Entity
@Table(name = "boolean_parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BooleanParameter implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "parameter_type_definition_id", insertable = false, updatable = false)
  private UUID parameterTypeDefinitionId;

  @Column(name = "default_value")
  private Boolean defaultValue;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parameter_type_definition_id", unique = true)
  private ParameterTypeDefinition parameterTypeDefinition;

  public BooleanParameter() {}

  public BooleanParameter(ParameterTypeDefinition parameterTypeDefinition) {
    this.parameterTypeDefinitionId = parameterTypeDefinition.getId();
    this.parameterTypeDefinition = parameterTypeDefinition;
  }

  public Boolean getDefaultValue() {
    return this.defaultValue;
  }

  public BooleanParameter defaultValue(Boolean defaultValue) {
    this.setDefaultValue(defaultValue);
    return this;
  }

  public void setDefaultValue(Boolean defaultValue) {
    this.defaultValue = defaultValue;
  }

  public ParameterTypeDefinition getParameterTypeDefinition() {
    return this.parameterTypeDefinition;
  }

  public void setParameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.parameterTypeDefinition = parameterTypeDefinition;
  }

  public BooleanParameter parameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.setParameterTypeDefinition(parameterTypeDefinition);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public String toString() {
    return "BooleanParameter{"
        + "defaultValue="
        + defaultValue
        + ", parameterTypeDefinition="
        + parameterTypeDefinition
        + '}';
  }
}
