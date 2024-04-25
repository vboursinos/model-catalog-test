package database.dto;

import java.util.UUID;

/** A DTO for the BooleanParameter entity */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BooleanParameterDTO {

  private UUID id;
  private Boolean defaultValue;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Boolean getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Boolean defaultValue) {
    this.defaultValue = defaultValue;
  }

  @Override
  public String toString() {
    return "BooleanParameterDTO{" + "id=" + id + ", defaultValue=" + defaultValue + '}';
  }
}
