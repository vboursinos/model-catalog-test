package database.dto;

import java.util.UUID;

/** A DTO for the ModelFamilyType entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelFamilyTypeDTO {

  private UUID id;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ModelFamilyTypeDTO{" + "name='" + name + '\'' + '}';
  }
}
