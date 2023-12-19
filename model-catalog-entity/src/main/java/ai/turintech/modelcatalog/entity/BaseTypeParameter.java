package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseTypeParameter extends AbstractUUIDIdentityEntity<UUID>
    implements Serializable {

  @Id
  @Column(name = "id", insertable = true, updatable = false)
  protected UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", unique = true)
  protected ParameterTypeDefinition parameterTypeDefinition;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ParameterTypeDefinition getParameterTypeDefinition() {
    return parameterTypeDefinition;
  }

  public void setParameterTypeDefinition(ParameterTypeDefinition parameterTypeDefinition) {
    this.parameterTypeDefinition = parameterTypeDefinition;
  }
}
