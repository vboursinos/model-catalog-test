package database.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ModelFamilyType. */
@Entity
@Table(name = "model_family_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelFamilyType {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "familyType")
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

  public ModelFamilyType name(String name) {
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
      this.models.forEach(i -> i.setFamilyType(null));
    }
    if (models != null) {
      models.forEach(i -> i.setFamilyType(this));
    }
    this.models = models;
  }

  public ModelFamilyType models(Set<Model> models) {
    this.setModels(models);
    return this;
  }

  public ModelFamilyType addModels(Model model) {
    this.models.add(model);
    model.setFamilyType(this);
    return this;
  }

  public ModelFamilyType removeModels(Model model) {
    this.models.remove(model);
    model.setFamilyType(null);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelFamilyType)) {
      return false;
    }
    return getId() != null && getId().equals(((ModelFamilyType) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "ModelFamilyType{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
  }
}
