package migration_files_creator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "name",
  "label",
  "description",
  "domain",
  "enabled",
  "defaultValue",
  "fixedValue",
  "constraint",
  "constraintInformation",
  "distribution",
})
public class HyperParameter {

  @JsonProperty("name")
  private String name;

  @JsonProperty("label")
  private String label;

  @JsonProperty("description")
  private String description;

  @JsonProperty("domain")
  private Domain domain;

  @JsonProperty("enabled")
  private Boolean enabled;

  @JsonProperty("defaultValue")
  private Object defaultValue;

  @JsonProperty("constraint")
  private Boolean constraint;

  @JsonProperty("constraintInformation")
  private String constraintInformation;

  @JsonProperty("distribution")
  private Distribution distribution;

  @JsonProperty("fixedValue")
  private boolean fixedValue;

  private int ordering;

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  @JsonProperty("label")
  public void setLabel(String label) {
    this.label = label;
  }

  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }

  @JsonProperty("domain")
  public Domain getDomain() {
    return domain;
  }

  @JsonProperty("domain")
  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  @JsonProperty("enabled")
  public Boolean getEnabled() {
    return enabled;
  }

  @JsonProperty("enabled")
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @JsonProperty("defaultValue")
  public Object getDefaultValue() {
    return defaultValue;
  }

  @JsonProperty("defaultValue")
  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  @JsonProperty("constraint")
  public Boolean getConstraint() {
    return constraint;
  }

  @JsonProperty("constraint")
  public void setConstraint(Boolean constraint) {
    this.constraint = constraint;
  }

  @JsonProperty("constraintInformation")
  public String getConstraintInformation() {
    return constraintInformation;
  }

  @JsonProperty("constraintInformation")
  public void setConstraintInformation(String constraintInformation) {
    this.constraintInformation = constraintInformation;
  }

  @JsonProperty("distribution")
  public Distribution getDistribution() {
    return distribution;
  }

  @JsonProperty("distribution")
  public void setDistribution(Distribution distribution) {
    this.distribution = distribution;
  }

  @JsonProperty("fixedValue")
  public boolean isFixedValue() {
    return fixedValue;
  }

  @JsonProperty("fixedValue")
  public void setFixedValue(boolean fixedValue) {
    this.fixedValue = fixedValue;
  }

  public int getOrdering() {
    return ordering;
  }

  public void setOrdering(int ordering) {
    this.ordering = ordering;
  }
}
