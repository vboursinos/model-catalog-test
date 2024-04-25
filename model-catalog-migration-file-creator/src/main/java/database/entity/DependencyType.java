package database.entity;

import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A DependencyType. */
@Entity
@Table(name = "dependency_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyType {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "dependency_group_id", referencedColumnName = "id")
  private DependencyGroupType dependencyGroupType;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

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
