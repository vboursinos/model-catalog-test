package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.model.pivottables;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.TableCreatorHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ModelTypePivot implements Pivot {

  public String buildInsertIntoPivotSQL(Model model, List<ModelDTO> dbModelList) {
    StringBuilder sb = new StringBuilder();
    boolean found = false;
    for (ModelDTO dbModel : dbModelList) {
      if (model.getName().equals(dbModel.getName())) {
        found = true;
        for (String modelType : model.getMetadata().getModelType()) {
          if (dbModel.getTypes().stream().noneMatch(g -> g.getName().equals(modelType))) {
            sb.append(createPivotQuerySQL(modelType, model.getName()));
            sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
            sb.append(createPivotQueryAuditSQL(modelType, model.getName(), 0));
          }
        }
      }
    }
    if (!found) {
      for (String modelType : model.getMetadata().getModelType()) {
        sb.append(createPivotQuerySQL(modelType, model.getName()));
        sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
        sb.append(createPivotQueryAuditSQL(modelType, model.getName(), 0));
      }
    }
    return sb + "\n";
  }

  public String buildDeleteSQLPivotTableNotExist(ModelDTO dbModel, Model jsonModel) {
    StringBuilder sb = new StringBuilder();
    List<ModelTypeDTO> modelTypesForDeletion = new ArrayList<>(dbModel.getTypes());
    if (jsonModel.getName().equals(dbModel.getName())) {
      for (ModelTypeDTO modelType : dbModel.getTypes()) {
        for (String jsonModelType : jsonModel.getMetadata().getModelType()) {
          if (modelType.getName().equals(jsonModelType)) {
            modelTypesForDeletion.remove(modelType);
          }
        }
      }
      for (ModelTypeDTO modelType : modelTypesForDeletion) {
        sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
        sb.append(createPivotQueryAuditSQL(modelType.getName(), dbModel.getName(), 2));
        sb.append(createDeletePivotQuerySQL(modelType, dbModel));
      }
    }
    return sb.toString();
  }

  public String buildInsertIntoPivotDeleteSQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    for (ModelTypeDTO modelType : model.getTypes()) {
      sb.append(TableCreatorHelper.buildRevInfoInsertSQL());
      sb.append(createPivotQueryAuditSQL(modelType.getName(), model.getName(), 2));
    }
    return sb.toString();
  }

  public StringBuilder createPivotQuerySQL(String value, String modelName) {
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO rel_model__model_type(model_id, model_type_id) VALUES ((select id from Model where name='")
        .append(modelName)
        .append("'),")
        .append("(select id from model_type where name='")
        .append(value)
        .append("'));\n");
  }

  public String createDeletePivotQuerySQL(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM rel_model__model_type WHERE model_id=(select id from model where name='")
        .append(model.getName())
        .append("');\n");
    return sb.toString();
  }

  private String createDeletePivotQuerySQL(ModelTypeDTO modelType, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    sb.append(
            "DELETE FROM rel_model__model_type WHERE model_type_id=(select id from model_type where name='")
        .append(modelType.getName())
        .append("') AND model_id=(select id from model where name='")
        .append(model.getName())
        .append("');\n");
    return sb.toString();
  }

  public String createPivotQueryAuditSQL(String value, String modelName, int action) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    return sb.append(
            "INSERT INTO rel_model__model_type_AUD(model_id, model_type_id, REVTYPE, REV, created_at, updated_at) VALUES ((select id from Model where name='")
        .append(modelName)
        .append("'),")
        .append("(select id from model_type where name='")
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
    return "rel_model__model_type";
  }
}
