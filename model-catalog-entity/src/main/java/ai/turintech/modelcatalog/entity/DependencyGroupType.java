package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A DependencyGroupType. */
@Entity
@Table(name = "dependency_group_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyGroupType extends AbstractUUIDIdentityEntity {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "dependencyGroupType")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Model> models = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "dependencyGroupType")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<DependencyType> dependencyTypes = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public String getName() {
    return this.name;
  }

  public DependencyGroupType name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Model> getModels() {
    return this.models;
  }

  public void setModels(Set<Model> models) {
    if (this.models != null) {
      this.models.forEach(i -> i.setDependencyGroupType(null));
    }
    if (models != null) {
      models.forEach(i -> i.setDependencyGroupType(this));
    }
    this.models = models;
  }

  public DependencyGroupType models(Set<Model> models) {
    this.setModels(models);
    return this;
  }

  public DependencyGroupType addModels(Model model) {
    this.models.add(model);
    model.setDependencyGroupType(this);
    return this;
  }

  public DependencyGroupType removeModels(Model model) {
    this.models.remove(model);
    model.setDependencyGroupType(null);
    return this;
  }

  public Set<DependencyType> getDependencyTypes() {
    return this.dependencyTypes;
  }

  public void setDependencyTypes(Set<DependencyType> dependencyTypes) {
    this.dependencyTypes = dependencyTypes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DependencyGroupType)) {
      return false;
    }
    return getId() != null && getId().equals(((DependencyGroupType) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "DependencyGroupType{"
        + "name='"
        + name
        + '\''
        + ", models="
        + models
        + ", dependencyTypes="
        + dependencyTypes
        + '}';
  }
}
