package database.dto;

import java.util.UUID;

/** A DTO for thFloatParameterRange} entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterRangeDTO {

  private UUID id;
  private Boolean isLeftOpen;

  private Boolean isRightOpen;

  private Double lower;
  private Double upper;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Boolean getIsLeftOpen() {
    return isLeftOpen;
  }

  public void setIsLeftOpen(Boolean isLeftOpen) {
    this.isLeftOpen = isLeftOpen;
  }

  public Boolean getIsRightOpen() {
    return isRightOpen;
  }

  public void setIsRightOpen(Boolean isRightOpen) {
    this.isRightOpen = isRightOpen;
  }

  public Double getLower() {
    return lower;
  }

  public void setLower(Double lower) {
    this.lower = lower;
  }

  public Double getUpper() {
    return upper;
  }

  public void setUpper(Double upper) {
    this.upper = upper;
  }

  @Override
  public String toString() {
    return "FloatParameterRangeDTO{"
        + "id="
        + id
        + ", isLeftOpen="
        + isLeftOpen
        + ", isRightOpen="
        + isRightOpen
        + ", lower="
        + lower
        + ", upper="
        + upper
        + '}';
  }
}
