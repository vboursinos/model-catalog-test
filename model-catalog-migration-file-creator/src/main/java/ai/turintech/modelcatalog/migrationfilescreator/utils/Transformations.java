package ai.turintech.modelcatalog.migrationfilescreator.utils;

import ai.turintech.modelcatalog.migrationfilescreator.model.Metadata;
import java.util.List;
import java.util.stream.Collectors;

public class Transformations {

  public static void replaceSingleQuotesInMetadata(Metadata metadata) {
    List<String> advantages =
        metadata.getAdvantages().stream()
            .map(s -> s.replace("'", "''"))
            .collect(Collectors.toList());
    metadata.setAdvantages(advantages);

    List<String> disadvantages =
        metadata.getDisadvantages().stream()
            .map(s -> s.replace("'", "''"))
            .collect(Collectors.toList());
    metadata.setDisadvantages(disadvantages);

    String modelDescription = metadata.getModelDescription().replace("'", "''");
    metadata.setModelDescription(modelDescription);
  }
}
