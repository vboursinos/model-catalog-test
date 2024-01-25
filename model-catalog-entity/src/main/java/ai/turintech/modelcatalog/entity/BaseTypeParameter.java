package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseTypeParameter extends AbstractUUIDIdentityEntity {

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
