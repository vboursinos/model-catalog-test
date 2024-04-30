package migration_files_creator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class EnsembleFamily {
  private static final String ENSEMBLE_FAMILIES_FILE = "ensemble-family.json";
  private static final String ENSEMBLE_FAMILIES_DIR = "model-catalog-migration-file-creator/static";

  @JsonProperty("model_name")
  private String name;

  @JsonProperty("family")
  private String family;

  @JsonProperty("ensembleType")
  private String ensembleType;

  public EnsembleFamily() {}

  public EnsembleFamily(String name, String family, String ensembleType) {
    this.name = name;
    this.family = family;
    this.ensembleType = ensembleType;
  }

  public static EnsembleFamily getEnsembleFamily(String modelName) {
    ObjectMapper objectMapper = new ObjectMapper();
    List<EnsembleFamily> ensembleFamilies = null;
    try {
      ensembleFamilies =
          objectMapper.readValue(
              new File(Paths.get(ENSEMBLE_FAMILIES_DIR, ENSEMBLE_FAMILIES_FILE).toString()),
              objectMapper
                  .getTypeFactory()
                  .constructCollectionType(List.class, EnsembleFamily.class));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    for (EnsembleFamily ensembleFamily : ensembleFamilies) {
      if (ensembleFamily.getName().equals(modelName)) {
        return ensembleFamily;
      }
    }
    return new EnsembleFamily(modelName, "other", "other");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFamily() {
    return family;
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public String getEnsembleType() {
    return ensembleType;
  }

  public void setEnsembleType(String ensembleType) {
    this.ensembleType = ensembleType;
  }
}
