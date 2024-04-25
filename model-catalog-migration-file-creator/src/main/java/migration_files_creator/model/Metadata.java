package migration_files_creator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
  private String model;
  private String modelDescription;
  private List<String> modelType;
  private List<String> advantages;
  private List<String> disadvantages;
  private List<String> prime;
  private String displayName;
  private Support supports;
  private String structure;
  private String dependencyGroup;

  public Metadata() {}

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getModelDescription() {
    return modelDescription;
  }

  public void setModelDescription(String modelDescription) {
    this.modelDescription = modelDescription;
  }

  public List<String> getModelType() {
    return modelType;
  }

  public void setModelType(List<String> modelType) {
    this.modelType = modelType;
  }

  public List<String> getAdvantages() {
    return advantages;
  }

  public void setAdvantages(List<String> advantages) {
    this.advantages = advantages;
  }

  public List<String> getDisadvantages() {
    return disadvantages;
  }

  public void setDisadvantages(List<String> disadvantages) {
    this.disadvantages = disadvantages;
  }

  public List<String> getPrime() {
    return prime;
  }

  public void setPrime(List<String> prime) {
    this.prime = prime;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Support getSupports() {
    return supports;
  }

  public void setSupports(Support supports) {
    this.supports = supports;
  }

  public String getStructure() {
    return structure;
  }

  public void setStructure(String structure) {
    this.structure = structure;
  }

  public String getDependencyGroup() {
    return dependencyGroup;
  }

  public void setDependencyGroup(String dependencyGroup) {
    this.dependencyGroup = dependencyGroup;
  }

  @Override
  public String toString() {
    return "Metadata{"
        + "model='"
        + model
        + '\''
        + ", modelDescription='"
        + modelDescription
        + '\''
        + ", modelType="
        + modelType
        + ", advantages="
        + advantages
        + ", disadvantages="
        + disadvantages
        + ", prime="
        + prime
        + ", displayName='"
        + displayName
        + '\''
        + ", supports="
        + supports
        + ", structure='"
        + structure
        + '\''
        + ", dependencyGroup='"
        + dependencyGroup
        + '\''
        + '}';
  }
}
