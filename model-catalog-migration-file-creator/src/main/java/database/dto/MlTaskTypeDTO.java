package database.dto;

import java.util.UUID;

/** A DTO for the MlTaskType entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MlTaskTypeDTO {

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

  // prettier-ignore
  @Override
  public String toString() {
    return "MlTaskTypeDTO{" + "id='" + getId() + "'" + ", name='" + getName() + "'" + "}";
  }
}
