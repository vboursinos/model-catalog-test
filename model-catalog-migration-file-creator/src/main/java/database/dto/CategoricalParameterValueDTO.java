package database.dto;

import java.util.UUID;

/** A DTO for the CategoricalParameterValue entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterValueDTO {

  private UUID id;
  private String value;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "CategoricalParameterValueDTO{" + "id=" + id + ", value='" + value + '\'' + '}';
  }
}
