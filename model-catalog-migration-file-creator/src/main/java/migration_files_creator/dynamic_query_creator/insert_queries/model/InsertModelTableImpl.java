package migration_files_creator.dynamic_query_creator.insert_queries.model;

import static migration_files_creator.dynamic_query_creator.insert_queries.model.ModelTableBuilder.*;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.Arrays;
import java.util.List;
import migration_files_creator.model.EnsembleFamily;
import migration_files_creator.model.Model;
import migration_files_creator.static_query_creator.TableCreatorHelper;
import org.springframework.stereotype.Component;

@Component
public class InsertModelTableImpl extends TableCreatorHelper implements InsertModelTable {
  public String buildInsertIntoModelSQL(Model jsonModel, List<ModelDTO> dbModelList) {

    String advantagesArray = buildArray(jsonModel.getMetadata().getAdvantages());
    String disadvantagesArray = buildArray(jsonModel.getMetadata().getDisadvantages());
    EnsembleFamily ensembleFamily = EnsembleFamily.getEnsembleFamily(jsonModel.getName());
    String description = normalizeDescription(jsonModel.getMetadata().getModelDescription());

    boolean found = false;
    for (ModelDTO dbModel : dbModelList) {
      if (jsonModel.getName().equals(dbModel.getName())) {
        if (compareModel(jsonModel, dbModel, ensembleFamily)) {
          found = true;
          break;
        } else {
          StringBuilder sb = new StringBuilder();
          sb.append(updateModelSQL(jsonModel, ensembleFamily));
          sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
          sb.append(ModelTableBuilder.insertModelAuditSQL(jsonModel, ensembleFamily, 1));
          return sb.toString();
        }
      }
    }
    if (!found) {
      StringBuilder sb = new StringBuilder();
      sb.append(
          insertModelSQL(
              jsonModel, ensembleFamily, advantagesArray, disadvantagesArray, description));
      sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
      sb.append(ModelTableBuilder.insertModelAuditSQL(jsonModel, ensembleFamily, 0));
      sb.append(includeAudit(jsonModel.getName()));
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

  private static boolean compareModel(
      Model jsonModel, ModelDTO dbModel, EnsembleFamily ensembleFamily) {
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
        && jsonModel.getMetadata().getSupports().getDecisionTree() == dbModel.getDecisionTree()
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

  private static String includeAudit(String name) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildRevInfoInsertSQL());
    sb.append(buildInsertAuditSQL(name, "model", 0));
    return sb.toString();
  }
}
