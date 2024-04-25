package migration_files_creator.insert_queries.dynamicTables.model.pivotTables;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import migration_files_creator.insert_queries.staticTables.TableCreatorHelper;
import migration_files_creator.model.Model;
import org.springframework.stereotype.Component;

@Component
public class GroupPivot implements Pivot {

  public String buildInsertIntoPivotSQL(Model model, List<ModelDTO> dbModelList) {
    StringBuilder sb = new StringBuilder();
    boolean found = false;
    for (ModelDTO dbModel : dbModelList) {
      if (model.getName().equals(dbModel.getName())) {
        found = true;
        for (String group : model.getGroups()) {
          if (dbModel.getGroups().stream().noneMatch(g -> g.getName().equals(group))) {
            sb.append(createPivotQuerySQL(group, model.getName()));
            sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
            sb.append(createPivotQueryAuditSQL(group, model.getName(), 0));
          }
        }
      }
    }
    if (!found) {
      for (String group : model.getGroups()) {
        sb.append(createPivotQuerySQL(group, model.getName()));
        sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
        sb.append(createPivotQueryAuditSQL(group, model.getName(), 0));
      }
    }
    return sb + "\n";
  }

  public String buildDeleteSQLPivotTableNotExist(
      ModelDTO dbModel, migration_files_creator.model.Model jsonModel) {
    StringBuilder sb = new StringBuilder();
    List<ModelGroupTypeDTO> groupForDeletion = new ArrayList<>(dbModel.getGroups());
    if (jsonModel.getName().equals(dbModel.getName())) {
      for (ModelGroupTypeDTO group : dbModel.getGroups()) {
        for (String jsonGroup : jsonModel.getGroups()) {
          if (group.getName().equals(jsonGroup)) {
            groupForDeletion.remove(group);
          }
        }
      }
      for (ModelGroupTypeDTO group : groupForDeletion) {
        sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
        sb.append(createPivotQueryAuditSQL(group.getName(), dbModel.getName(), 2));
        sb.append(createDeletePivotQuerySQL(group, dbModel));
      }
    }
    return sb.toString();
  }

  public String buildInsertIntoPivotDeleteSQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    for (ModelGroupTypeDTO group : model.getGroups()) {
      sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
      sb.append(createPivotQueryAuditSQL(group.getName(), model.getName(), 2));
    }
    return sb.toString();
  }

  public StringBuilder createPivotQuerySQL(String value, String modelName) {
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO rel_model__groups(model_id, group_id) VALUES ((select id from Model where name='")
        .append(modelName)
        .append("'),")
        .append("(select id from model_group_type where name='")
        .append(value)
        .append("'));\n");
  }

  public String createDeletePivotQuerySQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM rel_model__groups WHERE model_id=(select id from model where name='")
        .append(model.getName())
        .append("');\n");
    return sb.toString();
  }

  private String createDeletePivotQuerySQL(ModelGroupTypeDTO group, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append(
            "DELETE FROM rel_model__groups WHERE group_id=(select id from model_group_type where name='")
        .append(group.getName())
        .append("') AND model_id=(select id from model where name='")
        .append(model.getName())
        .append("'));\n");
    return sb.toString();
  }

  public String createPivotQueryAuditSQL(String value, String modelName, int action) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO rel_model__groups_AUD(model_id, group_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='")
        .append(modelName)
        .append("'),")
        .append("(select id from model_group_type where name='")
        .append(value)
        .append("'),")
        .append(action)
        .append(", (select max(rev) from revinfo), '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n")
        .toString();
  }

  public String getTableName() {
    return "rel_model__groups";
  }
}
