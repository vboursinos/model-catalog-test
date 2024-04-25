package database.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A MlTaskType. */
@Entity
@Table(name = "ml_task_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MlTaskType {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "mlTask")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Model> models = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public MlTaskType name(String name) {
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
      this.models.forEach(i -> i.setMlTask(null));
    }
    if (models != null) {
      models.forEach(i -> i.setMlTask(this));
    }
    this.models = models;
  }

  public MlTaskType models(Set<Model> models) {
    this.setModels(models);
    return this;
  }

  public MlTaskType addModels(Model model) {
    this.models.add(model);
    model.setMlTask(this);
    return this;
  }

  public MlTaskType removeModels(Model model) {
    this.models.remove(model);
    model.setMlTask(null);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MlTaskType)) {
      return false;
    }
    return getId() != null && getId().equals(((MlTaskType) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "MlTaskType{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
  }
}
