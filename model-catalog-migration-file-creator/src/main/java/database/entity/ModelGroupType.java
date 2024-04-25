package database.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ModelGroupType. */
@Entity
@Table(name = "model_group_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelGroupType {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
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

  public ModelGroupType name(String name) {
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
      this.models.forEach(i -> i.removeGroups(this));
    }
    if (models != null) {
      models.forEach(i -> i.addGroups(this));
    }
    this.models = models;
  }

  public ModelGroupType models(Set<Model> models) {
    this.setModels(models);
    return this;
  }

  public ModelGroupType addModels(Model model) {
    this.models.add(model);
    model.getGroups().add(this);
    return this;
  }

  public ModelGroupType removeModels(Model model) {
    this.models.remove(model);
    model.getGroups().remove(this);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelGroupType)) {
      return false;
    }
    return getId() != null && getId().equals(((ModelGroupType) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "ModelGroupType{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
  }
}
