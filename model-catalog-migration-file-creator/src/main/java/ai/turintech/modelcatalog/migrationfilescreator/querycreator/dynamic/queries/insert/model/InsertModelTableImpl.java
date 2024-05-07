package ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model;

import static ai.turintech.modelcatalog.migrationfilescreator.querycreator.dynamic.queries.insert.model.ModelTableBuilder.*;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.migrationfilescreator.exceptions.ModelCatalogMigrationFileException;
import ai.turintech.modelcatalog.migrationfilescreator.model.EnsembleFamily;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.querycreator.constant.TableCreatorHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InsertModelTableImpl extends TableCreatorHelper implements InsertModelTable {

  @Value("${ensemble_family_json_path}")
  private String ensembleFamilyJsonPath;

  @Value("${decision_tree_json_path}")
  private String decisionTreeJsonFilePath;

  public String buildInsertIntoModelSQL(Model jsonModel, List<ModelDTO> dbModelList) {

    String advantagesArray = buildArray(jsonModel.getMetadata().getAdvantages());
    String disadvantagesArray = buildArray(jsonModel.getMetadata().getDisadvantages());
    EnsembleFamily ensembleFamily = getEnsembleFamily(jsonModel.getName());
    String description = normalizeDescription(jsonModel.getMetadata().getModelDescription());

    boolean found = false;
    for (ModelDTO dbModel : dbModelList) {
      if (jsonModel.getName().equals(dbModel.getName())) {
        if (compareModel(jsonModel, dbModel, ensembleFamily)) {
          found = true;
          break;
        } else {
          StringBuilder sb = new StringBuilder();
          sb.append(
              updateModelSQL(jsonModel, ensembleFamily, isDecisionTreeModel(jsonModel.getName())));
          sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
          sb.append(
              ModelTableBuilder.insertModelAuditSQL(
                  jsonModel, ensembleFamily, isDecisionTreeModel(jsonModel.getName()), 1));
          return sb.toString();
        }
      }
    }
    if (!found) {
      StringBuilder sb = new StringBuilder();
      sb.append(
          insertModelSQL(
              jsonModel,
              ensembleFamily,
              advantagesArray,
              disadvantagesArray,
              description,
              isDecisionTreeModel(jsonModel.getName())));
      sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
      sb.append(
          ModelTableBuilder.insertModelAuditSQL(
              jsonModel, ensembleFamily, isDecisionTreeModel(jsonModel.getName()), 0));
      return sb.toString();
    }

    return "\n";
  }

  private String buildArray(List<String> modelList) {
    return "{" + String.join(",", modelList) + "}";
  }

  private String normalizeDescription(String description) {
    return description.replaceAll("\\n", "");
  }

  private boolean compareModel(Model jsonModel, ModelDTO dbModel, EnsembleFamily ensembleFamily) {
    String jsonDescription = normalizeString(jsonModel.getMetadata().getModelDescription());
    String dbDescription = normalizeString(dbModel.getDescription());

    String jsonAdvantages = jsonModel.getMetadata().getAdvantages().toString();
    String jsonDisadvantages = jsonModel.getMetadata().getDisadvantages().toString();
    String dbAdvantages = normalizeList(Arrays.asList(dbModel.getAdvantages()));
    String dbDisadvantages = normalizeList(Arrays.asList(dbModel.getDisadvantages()));

    return jsonModel.getMlTask().equals(dbModel.getMlTask().getName())
        && jsonDescription.equals(dbDescription)
        && jsonModel.getMetadata().getDisplayName().equals(dbModel.getDisplayName())
        && jsonAdvantages.equals(dbAdvantages)
        && jsonDisadvantages.equals(dbDisadvantages)
        && jsonModel.getMetadata().getStructure().equals(dbModel.getStructure().getName())
        && jsonModel.isBlackListed() == !dbModel.getEnabled()
        && isDecisionTreeModel(jsonModel.getName()) == dbModel.getDecisionTree()
        && ensembleFamily.getFamily().equals(dbModel.getFamilyType().getName())
        && ensembleFamily.getEnsembleType().equals(dbModel.getEnsembleType().getName())
        && jsonModel
            .getMetadata()
            .getDependencyGroup()
            .equals(dbModel.getDependencyGroupType().getName());
  }

  private static String normalizeString(String input) {
    return input.replaceAll("\\n", "").replaceAll("''", "'").replaceAll("\\s", "");
  }

  private static String normalizeList(List<?> list) {
    return list.toString().replaceAll("'", "''");
  }

  public boolean isDecisionTreeModel(String modelName) {
    ObjectMapper objectMapper = new ObjectMapper();
    File jsonFile = new File(decisionTreeJsonFilePath);
    Map<String, Map<String, Boolean>> decisionTreeMap = null;
    try {
      decisionTreeMap = objectMapper.readValue(jsonFile, new TypeReference<>() {});
    } catch (IOException e) {
      throw new ModelCatalogMigrationFileException(e);
    }
    Map<String, Boolean> modelMap = decisionTreeMap.get(modelName);
    return modelMap != null
        && modelMap.containsKey("decision_tree")
        && modelMap.get("decision_tree");
  }

  public EnsembleFamily getEnsembleFamily(String modelName) {
    ObjectMapper objectMapper = new ObjectMapper();
    List<EnsembleFamily> ensembleFamilies = null;
    try {
      ensembleFamilies =
          objectMapper.readValue(
              new File(Paths.get(ensembleFamilyJsonPath).toString()),
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
}
