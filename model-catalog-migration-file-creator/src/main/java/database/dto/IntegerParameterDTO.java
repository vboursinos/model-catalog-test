package database.dto;

import java.util.*;

/** A DTO for the IntegerParameter entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterDTO {

  private UUID id;
  private Integer defaultValue;

  private Set<IntegerParameterValueDTO> integerParameterValues = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Integer getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Integer defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Set<IntegerParameterValueDTO> getIntegerParameterValues() {
    return integerParameterValues;
  }

  public void setIntegerParameterValues(Set<IntegerParameterValueDTO> integerParameterValues) {
    this.integerParameterValues = integerParameterValues;
  }

  @Override
  public String toString() {
    return "IntegerParameterDTO{"
        + "id="
        + id
        + ", defaultValue="
        + defaultValue
        + ", integerParameterValues="
        + integerParameterValues
        + '}';
  }
}
