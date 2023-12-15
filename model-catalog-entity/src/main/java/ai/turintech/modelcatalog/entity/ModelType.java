package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ModelType. */
@Entity
@Table(name = "model_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelType extends AbstractUUIDIdentityEntity<UUID> implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Model> models = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public String getName() {
    return this.name;
  }

  public ModelType name(String name) {
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
      this.models.forEach(i -> i.setType(null));
    }
    if (models != null) {
      models.forEach(i -> i.setType(this));
    }
    this.models = models;
  }

  public ModelType models(Set<Model> models) {
    this.setModels(models);
    return this;
  }

  public ModelType addModels(Model model) {
    this.models.add(model);
    model.setType(this);
    return this;
  }

  public ModelType removeModels(Model model) {
    this.models.remove(model);
    model.setType(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelType)) {
      return false;
    }
    return getId() != null && getId().equals(((ModelType) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ModelType{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
  }
}
