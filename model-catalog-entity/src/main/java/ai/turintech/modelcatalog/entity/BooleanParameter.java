package ai.turintech.modelcatalog.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/** A BooleanParameter. */
@Entity
@Table(name = "boolean_parameter")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BooleanParameter extends ParameterTypeDefinition implements Serializable {

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

  @Override
  public String toString() {
    return "BooleanParameter{" + "defaultValue=" + defaultValue + '}';
  }
}
