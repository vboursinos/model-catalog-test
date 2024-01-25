package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ModelEnsembleType. */
@Entity
@Table(name = "model_ensemble_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelEnsembleType extends AbstractUUIDIdentityEntity {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "ensembleType")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Model> models = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public String getName() {
    return this.name;
  }

  public ModelEnsembleType name(String name) {
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
      this.models.forEach(i -> i.setEnsembleType(null));
    }
    if (models != null) {
      models.forEach(i -> i.setEnsembleType(this));
    }
    this.models = models;
  }

  public ModelEnsembleType models(Set<Model> models) {
    this.setModels(models);
    return this;
  }

  public ModelEnsembleType addModels(Model model) {
    this.models.add(model);
    model.setEnsembleType(this);
    return this;
  }

  public ModelEnsembleType removeModels(Model model) {
    this.models.remove(model);
    model.setEnsembleType(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelEnsembleType)) {
      return false;
    }
    return getId() != null && getId().equals(((ModelEnsembleType) o).getId());
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
    return "ModelEnsembleType{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
  }
}
