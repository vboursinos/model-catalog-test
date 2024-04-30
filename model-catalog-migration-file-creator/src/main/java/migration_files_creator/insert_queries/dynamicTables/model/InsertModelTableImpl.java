package migration_files_creator.insert_queries.dynamicTables.model;

import static migration_files_creator.insert_queries.dynamicTables.model.ModelTableBuilder.*;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.Arrays;
import java.util.List;
import migration_files_creator.insert_queries.staticTables.TableCreatorHelper;
import migration_files_creator.model.EnsembleFamily;
import migration_files_creator.model.Model;
import org.springframework.stereotype.Component;

@Component
public class InsertModelTableImpl extends TableCreatorHelper implements InsertModelTable {
  public String buildInsertIntoModelSQL(Model jsonModel, List<ModelDTO> dbModelList) {
    String advantagesArray = "{" + String.join(",", jsonModel.getMetadata().getAdvantages()) + "}";
    String disadvantagesArray =
        "{" + String.join(",", jsonModel.getMetadata().getDisadvantages()) + "}";
    EnsembleFamily ensembleFamily = EnsembleFamily.getEnsembleFamily(jsonModel.getName());

    String description = jsonModel.getMetadata().getModelDescription().replaceAll("\\n", "");
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

  private static boolean compareModel(
      Model jsonModel, ModelDTO dbModel, EnsembleFamily ensembleFamily) {
    String jsonDescription =
        jsonModel
            .getMetadata()
            .getModelDescription()
            .replaceAll("\\n", "")
            .replaceAll("''", "'")
            .replaceAll("\\s", "");
    String dbDescription =
        dbModel.getDescription().replaceAll("''", "'").replaceAll("\\n", "").replaceAll("\\s", "");

    String jsonAdvantages = jsonModel.getMetadata().getAdvantages().toString();
    String jsonDisadvantages = jsonModel.getMetadata().getDisadvantages().toString();
    String dbAdvantages =
        Arrays.stream(dbModel.getAdvantages()).toList().toString().replaceAll("'", "''");
    String dbDisadvantages =
        Arrays.stream(dbModel.getDisadvantages()).toList().toString().replaceAll("'", "''");

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

  private static String includeAudit(String name) {
    StringBuilder sb = new StringBuilder();
    sb.append(buildRevInfoInsertSQL());
    sb.append(buildInsertAuditSQL(name, "model", 0));
    return sb.toString();
  }
}
