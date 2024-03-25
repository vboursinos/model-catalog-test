package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A DependencyType. */
@Entity
@Table(name = "dependency_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyType extends AbstractUUIDIdentityEntity {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dependency_group_id", referencedColumnName = "id")
  private DependencyGroupType dependencyGroupType;

  public String getName() {
    return this.name;
  }

  public DependencyType name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DependencyGroupType getDependencyGroupType() {
    return this.dependencyGroupType;
  }

  public void setDependencyGroupType(DependencyGroupType dependencyGroupType) {
    this.dependencyGroupType = dependencyGroupType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DependencyType)) {
      return false;
    }
    return getId() != null && getId().equals(((DependencyType) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
