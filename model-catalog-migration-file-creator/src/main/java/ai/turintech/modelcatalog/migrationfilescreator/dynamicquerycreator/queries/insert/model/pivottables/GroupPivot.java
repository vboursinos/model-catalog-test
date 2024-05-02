package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.queries.insert.model.pivottables;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.migrationfilescreator.exceptions.ModelCatalogMigrationFileException;
import ai.turintech.modelcatalog.migrationfilescreator.model.Model;
import ai.turintech.modelcatalog.migrationfilescreator.staticquerycreator.TableCreatorHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GroupPivot implements Pivot {

  private static final String GROUP_TYPE_JSON_FILE_PATH =
      "model-catalog-migration-file-creator/static/groups.json";
  private final ObjectMapper mapper = new ObjectMapper();

  public String buildInsertIntoPivotSQL(Model model, List<ModelDTO> dbModelList) {
    StringBuilder sb = new StringBuilder();
    boolean found = false;
    for (ModelDTO dbModel : dbModelList) {
      if (model.getName().equals(dbModel.getName())) {
        found = true;
        for (String group : getModelGroups(model.getName())) {
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

  public String buildDeleteSQLPivotTableNotExist(ModelDTO dbModel, Model jsonModel) {
    StringBuilder sb = new StringBuilder();
    List<ModelGroupTypeDTO> groupForDeletion = new ArrayList<>(dbModel.getGroups());
    if (jsonModel.getName().equals(dbModel.getName())) {
      for (ModelGroupTypeDTO group : dbModel.getGroups()) {
        for (String jsonGroup : getModelGroups(jsonModel.getName())) {
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

  public List<String> getModelGroups(String modelName) {
    Map<String, Map<String, List<String>>> modelData;
    try {
      modelData = getModelGroupTypes(GROUP_TYPE_JSON_FILE_PATH);
    } catch (IOException e) {
      throw new ModelCatalogMigrationFileException(e);
    }
    return getModelGroups(modelName, modelData);
  }

  public Map<String, Map<String, List<String>>> getModelGroupTypes(String filePath)
      throws IOException {
    File jsonFile = new File(filePath);
    MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, String.class, Map.class);
    return mapper.readValue(jsonFile, mapType);
  }

  public static List<String> getModelGroups(
      String modelName, Map<String, Map<String, List<String>>> modelData) {
    List<String> groups = new ArrayList<>();
    for (Map.Entry<String, Map<String, List<String>>> entry : modelData.entrySet()) {
      String group = entry.getKey();
      Map<String, List<String>> modelMap = entry.getValue();
      for (Map.Entry<String, List<String>> modelEntry : modelMap.entrySet()) {
        List<String> models = modelEntry.getValue();
        if (models.contains(modelName)) {
          groups.add(group);
          break;
        }
      }
    }
    return groups;
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
        .append("');\n");
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
