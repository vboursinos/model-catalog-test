package database.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseTypeParameter {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", unique = true)
  protected ParameterTypeDefinition parameterTypeDefinition;

  public ParameterTypeDefinition getParameterTypeDefinition() {
    return parameterTypeDefinition;
  }

  public void setParameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.parameterTypeDefinition = parameterTypeDefinition;
  }
}
