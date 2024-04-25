package database.dto;

import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependencyTypeDTO {

  private static final long serialVersionUID = -536759035872405018L;

  private UUID id;
  private String name;

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

  @Override
  public String toString() {
    return "DependencyTypeDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
