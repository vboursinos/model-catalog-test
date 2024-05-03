package ai.turintech.modelcatalog.migrationfilescreator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"categories"})
public class CategoricalSet {
  @JsonProperty("categories")
  private List<String> categories;

  @JsonProperty("categories")
  public List<String> getCategories() {
    return categories;
  }

  @JsonProperty("categories")
  public void setCategories(List<String> categories) {
    this.categories = categories;
  }
}
