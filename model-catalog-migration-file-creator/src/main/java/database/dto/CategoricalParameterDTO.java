package database.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/** A DTO for the CategoricalParameter entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterDTO {
  private UUID id;
  private String defaultValue;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  private Set<CategoricalParameterValueDTO> categoricalParameterValues = new HashSet<>();;

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Set<CategoricalParameterValueDTO> getCategoricalParameterValues() {
    return categoricalParameterValues;
  }

  public void setCategoricalParameterValues(
      Set<CategoricalParameterValueDTO> categoricalParameterValues) {
    this.categoricalParameterValues = categoricalParameterValues;
  }

  @Override
  public String toString() {
    return "CategoricalParameterDTO{"
        + "id="
        + id
        + ", defaultValue='"
        + defaultValue
        + '\''
        + ", categoricalParameterValues="
        + categoricalParameterValues
        + '}';
  }
}
