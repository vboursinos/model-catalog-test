package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractEntity;
import jakarta.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseTypeParameter extends AbstractEntity {

  @Id
  @Column(name = "parameter_type_definition_id", insertable = true, updatable = false)
  protected UUID parameterTypeDefinitionId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parameter_type_definition_id", unique = true)
  protected ParameterTypeDefinition parameterTypeDefinition;

  public UUID getParameterTypeDefinitionId() {
    return parameterTypeDefinitionId;
  }

  public void setParameterTypeDefinitionId(UUID parameterTypeDefinitionId) {
    this.parameterTypeDefinitionId = parameterTypeDefinitionId;
  }

  public ParameterTypeDefinition getParameterTypeDefinition() {
    return parameterTypeDefinition;
  }

  public void setParameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.parameterTypeDefinition = parameterTypeDefinition;
  }
}
