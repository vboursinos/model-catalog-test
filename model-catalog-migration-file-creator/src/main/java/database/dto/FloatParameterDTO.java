package database.dto;

import java.util.*;

/** A DTO for the FloatParameter entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterDTO {

  private UUID id;
  private Double defaultValue;
  private Set<FloatParameterRangeDTO> floatParameterRanges = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Double getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Double defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Set<FloatParameterRangeDTO> getFloatParameterRanges() {
    return floatParameterRanges;
  }

  public void setFloatParameterRanges(Set<FloatParameterRangeDTO> floatParameterRanges) {
    this.floatParameterRanges = floatParameterRanges;
  }

  @Override
  public String toString() {
    return "FloatParameterDTO{"
        + "id="
        + id
        + ", defaultValue="
        + defaultValue
        + ", floatParameterRanges="
        + floatParameterRanges
        + '}';
  }
}
