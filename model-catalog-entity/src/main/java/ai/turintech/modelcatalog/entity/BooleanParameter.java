package ai.turintech.modelcatalog.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A BooleanParameter. */
@Entity
@Table(name = "boolean_parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BooleanParameter extends BaseTypeParameter {

  private static final long serialVersionUID = 1L;

  @Column(name = "default_value")
  private Boolean defaultValue;

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

  public BooleanParameter parameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.setParameterTypeDefinition(parameterTypeDefinition);
    return this;
  }

  @Override
  public String toString() {
    return "BooleanParameter{"
        + "defaultValue="
        + defaultValue
        + ", parameterTypeDefinition="
        + super.getParameterTypeDefinition()
        + '}';
  }
}
