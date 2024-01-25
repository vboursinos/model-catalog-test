package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A Metric. */
@Entity
@Table(name = "metric")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Metric extends AbstractUUIDIdentityEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "name")
  private String metric;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "incompatibleMetrics")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Model> models = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public String getMetric() {
    return this.metric;
  }

  public Metric metric(String metric) {
    this.setMetric(metric);
    return this;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public Set<Model> getModels() {
    return this.models;
  }

  public void setModels(Set<Model> models) {
    if (this.models != null) {
      this.models.forEach(i -> i.removeIncompatibleMetrics(this));
    }
    if (models != null) {
      models.forEach(i -> i.addIncompatibleMetrics(this));
    }
    this.models = models;
  }

  public Metric models(Set<Model> models) {
    this.setModels(models);
    return this;
  }

  public Metric addModel(Model model) {
    this.models.add(model);
    model.getIncompatibleMetrics().add(this);
    return this;
  }

  public Metric removeModel(Model model) {
    this.models.remove(model);
    model.getIncompatibleMetrics().remove(this);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Metric)) {
      return false;
    }
    return getId() != null && getId().equals(((Metric) o).getId());
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
    return "Metric{" + "id=" + getId() + ", metric='" + getMetric() + "'" + "}";
  }
}
