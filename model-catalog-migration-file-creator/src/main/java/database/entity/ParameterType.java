package database.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A ParameterType. */
@Entity
@Table(name = "parameter_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterType {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<ParameterTypeDefinition> definitions = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public ParameterType name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<ParameterTypeDefinition> getDefinitions() {
    return this.definitions;
  }

  public void setDefinitions(Set<ParameterTypeDefinition> parameterTypeDefinitions) {
    if (this.definitions != null) {
      this.definitions.forEach(i -> i.setType(null));
    }
    if (parameterTypeDefinitions != null) {
      parameterTypeDefinitions.forEach(i -> i.setType(this));
    }
    this.definitions = parameterTypeDefinitions;
  }

  public ParameterType definitions(Set<ParameterTypeDefinition> parameterTypeDefinitions) {
    this.setDefinitions(parameterTypeDefinitions);
    return this;
  }

  public ParameterType addDefinitions(ParameterTypeDefinition parameterTypeDefinition) {
    this.definitions.add(parameterTypeDefinition);
    parameterTypeDefinition.setType(this);
    return this;
  }

  public ParameterType removeDefinitions(ParameterTypeDefinition parameterTypeDefinition) {
    this.definitions.remove(parameterTypeDefinition);
    parameterTypeDefinition.setType(null);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParameterType)) {
      return false;
    }
    return getId() != null && getId().equals(((ParameterType) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "ParameterType{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
  }
}
