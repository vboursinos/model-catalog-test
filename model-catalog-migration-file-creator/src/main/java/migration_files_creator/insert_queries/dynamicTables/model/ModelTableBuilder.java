package migration_files_creator.insert_queries.dynamicTables.model;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.text.SimpleDateFormat;
import migration_files_creator.model.EnsembleFamily;
import migration_files_creator.model.Model;

public class ModelTableBuilder {
  public static String updateModelSQL(Model model, EnsembleFamily ensembleFamily) {
    String advantagesArray = "{" + String.join(",", model.getMetadata().getAdvantages()) + "}";
    String disadvantagesArray =
        "{" + String.join(",", model.getMetadata().getDisadvantages()) + "}";

    String description = model.getMetadata().getModelDescription().replaceAll("\\n", "");

    String modelSqlQuery =
        String.format(
            "UPDATE model SET ml_task_id=(select id from ml_task_type where name='%s'), description='%s', display_name='%s', structure_id=(select id from model_structure_type where name='%s'), advantages='%s', disadvantages='%s', enabled=%b, ensemble_type_id=(select id from model_ensemble_type where name='%s'), family_type_id=(select id from model_family_type where name='%s'), decision_tree=%b, dependency_group_id=(select id from dependency_group_type where name='%s') WHERE name='%s';\n",
            model.getMlTask(),
            description,
            model.getMetadata().getDisplayName(),
            model.getMetadata().getStructure(),
            advantagesArray,
            disadvantagesArray,
            !model.isBlackListed(),
            ensembleFamily.getEnsembleType(),
            ensembleFamily.getFamily(),
            model.getMetadata().getSupports().getDecisionTree(),
            model.getMetadata().getDependencyGroup(),
            model.getName());

    return modelSqlQuery + "\n";
  }

  public static String insertModelSQL(
      Model model,
      EnsembleFamily ensembleFamily,
      String advantagesArray,
      String disadvantagesArray,
      String description) {
    return String.format(
        "INSERT INTO model(name, ml_task_id, description, display_name, structure_id, advantages, disadvantages, enabled, ensemble_type_id, family_type_id, decision_tree, dependency_group_id) VALUES ('%s', (select id from ml_task_type where name='%s'),'%s', '%s', (select id from model_structure_type where name='%s'), '%s', '%s', %b, (select id from model_ensemble_type where name='%s'),(select id from model_family_type where name='%s'), %b, (select id from dependency_group_type where name='%s'));\n",
        model.getName(),
        model.getMlTask(),
        description,
        model.getMetadata().getDisplayName(),
        model.getMetadata().getStructure(),
        advantagesArray,
        disadvantagesArray,
        !model.isBlackListed(),
        ensembleFamily.getEnsembleType(),
        ensembleFamily.getFamily(),
        model.getMetadata().getSupports().getDecisionTree(),
        model.getMetadata().getDependencyGroup());
  }

  public static StringBuilder insertModelAuditSQL(
      Model model, EnsembleFamily ensembleFamily, int revType) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    String advantagesArray = "{" + String.join(",", model.getMetadata().getAdvantages()) + "}";
    String disadvantagesArray =
        "{" + String.join(",", model.getMetadata().getDisadvantages()) + "}";
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO model_aud(id, rev, revtype, created_at, updated_at, ml_task_id, name, display_name, structure_id, description, advantages, disadvantages, enabled, ensemble_type_id, family_type_id, decision_tree, dependency_group_id) VALUES ((select id from model where name = '")
        .append(model.getName())
        .append("'), (select max(rev) from revinfo),")
        .append(revType)
        .append(", '")
        .append(date)
        .append("', '")
        .append(date)
        .append("', (select id from ml_task_type where name = '")
        .append(model.getMlTask())
        .append("'), '")
        .append(model.getName())
        .append("', '")
        .append(model.getMetadata().getDisplayName())
        .append("', (select id from model_structure_type where name = '")
        .append(model.getMetadata().getStructure())
        .append("'), '")
        .append(model.getMetadata().getModelDescription())
        .append("', '")
        .append(advantagesArray)
        .append("', '")
        .append(disadvantagesArray)
        .append("', ")
        .append(!model.isBlackListed())
        .append(", (select id from model_ensemble_type where name = '")
        .append(ensembleFamily.getEnsembleType())
        .append("'), (select id from model_family_type where name = '")
        .append(ensembleFamily.getFamily())
        .append("'), ")
        .append(model.getMetadata().getSupports().getDecisionTree())
        .append(", (select id from dependency_group_type where name = '")
        .append(model.getMetadata().getDependencyGroup())
        .append("'));\n");
  }

  public static StringBuilder insertModelAuditSQLDelete(ModelDTO model) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO model_aud(id, rev, revtype, created_at, updated_at, ml_task_id, name, display_name, structure_id, description, advantages, disadvantages, enabled, ensemble_type_id, family_type_id, decision_tree, dependency_group_id) VALUES ((select id from model where name = '")
        .append(model.getName())
        .append("'), (select max(rev) from revinfo), 2, '")
        .append(date)
        .append("', '")
        .append(date)
        .append("', null, null, null, null, null, null, null, null, null, null, null, null);\n");
  }
}
