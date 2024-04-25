package database.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyGroupTypeDTO {

  private static final long serialVersionUID = -536759035872405018L;

  private UUID id;
  private String name;
  private Set<DependencyTypeDTO> dependencyTypes = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<DependencyTypeDTO> getDependencyTypes() {
    return this.dependencyTypes;
  }

  public void setDependencyTypes(Set<DependencyTypeDTO> dependencyTypes) {
    this.dependencyTypes = dependencyTypes;
  }

  @Override
  public String toString() {
    return "DependencyGroupTypeDTO{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", dependencyTypes="
        + dependencyTypes
        + '}';
  }
}
