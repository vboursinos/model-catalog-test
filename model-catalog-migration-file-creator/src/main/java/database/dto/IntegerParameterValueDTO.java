package database.dto;

import java.util.UUID;

/** A DTO for the IntegerParameterValue entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterValueDTO {

  private UUID id;
  private Integer lower;

  private Integer upper;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Integer getLower() {
    return lower;
  }

  public void setLower(Integer lower) {
    this.lower = lower;
  }

  public Integer getUpper() {
    return upper;
  }

  public void setUpper(Integer upper) {
    this.upper = upper;
  }

  @Override
  public String toString() {
    return "IntegerParameterValueDTO{" + "lower=" + lower + ", upper=" + upper + '}';
  }
}
