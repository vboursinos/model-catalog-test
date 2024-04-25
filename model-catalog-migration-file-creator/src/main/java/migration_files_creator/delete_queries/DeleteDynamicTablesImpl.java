package migration_files_creator.delete_queries;

import static migration_files_creator.delete_queries.DeleteTableBuilder.buildDeleteParameterSQL;
import static migration_files_creator.insert_queries.dynamicTables.parameters.ParameterTablesBuilder.updateParameterSQL;
import static migration_files_creator.insert_queries.staticTables.TableCreatorHelper.buildRevInfoInsertSQL;

import database.dto.ModelDTO;
import database.dto.ParameterDTO;
import database.dto.ParameterTypeDefinitionDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import migration_files_creator.insert_queries.dynamicTables.model.ModelTableBuilder;
import migration_files_creator.insert_queries.dynamicTables.model.pivotTables.Pivot;
import migration_files_creator.insert_queries.dynamicTables.parameters.CompareParametersService;
import migration_files_creator.insert_queries.dynamicTables.parameters.InsertParametersTablesImpl;
import migration_files_creator.insert_queries.dynamicTables.parameters.ParameterTablesBuilder;
import migration_files_creator.insert_queries.dynamicTables.parameters.typeParameters.ParameterStrategy;
import migration_files_creator.insert_queries.dynamicTables.parameters.typeParameters.TypeParameterStrategyFactoryImpl;
import migration_files_creator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteDynamicTablesImpl implements DeleteDynamicTables {

  @Autowired private TypeParameterStrategyFactoryImpl typeParameterStrategyFactory;

  @Autowired private List<Pivot> pivotTables;

  @Autowired private CompareParametersService compareParametersService;

  @Transactional
  public String buildDeleteSQL(String mltask, Models models, List<ModelDTO> modelsDTO) {
    StringBuilder sb = new StringBuilder();
    List<ModelDTO> modelsForDeletion = new ArrayList<>(modelsDTO);
    for (ModelDTO dbModel : modelsDTO) {

      for (migration_files_creator.model.Model jsonModel : models.getModels()) {
        if (jsonModel.getName().equals(dbModel.getName())) {
          modelsForDeletion.remove(dbModel);
          sb.append(buildDeleteSQLParameterNotExist(dbModel, jsonModel));
          for (Pivot pivot : pivotTables) {
            sb.append(pivot.buildDeleteSQLPivotTableNotExist(dbModel, jsonModel));
          }
        }
      }
    }
    sb.append(buildDeleteSQLModelNotExist(mltask, modelsForDeletion));
    return sb.toString();
  }

  public String buildDeleteSQLModelNotExist(String mltask, List<ModelDTO> modelsDTO) {
    StringBuilder sb = new StringBuilder();
    for (ModelDTO model : modelsDTO) {
      if (model.getMlTask().getName().equals(mltask)) {
        // Add audit commands
        sb.append(addAUDCommandsIfModelNotExist(model));
        sb.append(buildRevInfoInsertSQL());
        sb.append(ModelTableBuilder.insertModelAuditSQLDelete(model));

        sb.append(
            DeleteTableBuilder.buildDeleteParameterTypeValueSql(
                model, "categorical_parameter_value"));
        sb.append(
            DeleteTableBuilder.buildDeleteParameterTypeValueSql(model, "integer_parameter_value"));
        sb.append(
            DeleteTableBuilder.buildDeleteParameterTypeValueSql(model, "float_parameter_range"));
        sb.append(DeleteTableBuilder.buildDeleteParameterTypeSql(model, "categorical_parameter"));
        sb.append(DeleteTableBuilder.buildDeleteParameterTypeSql(model, "integer_parameter"));
        sb.append(DeleteTableBuilder.buildDeleteParameterTypeSql(model, "float_parameter"));
        sb.append(DeleteTableBuilder.buildDeleteParameterTypeSql(model, "boolean_parameter"));
        sb.append(DeleteTableBuilder.buildDeleteParameterTypeDefinitionSql(model));
        sb.append(buildDeleteParameterSQL(model));
        for (Pivot pivot : pivotTables) {
          sb.append(pivot.createDeletePivotQuerySQL(model));
        }
        sb.append(DeleteTableBuilder.buildDeleteSQL(model));
      }
    }
    return sb.toString();
  }

  public String addAUDCommandsIfModelNotExist(ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    for (Pivot pivot : pivotTables) {
      sb.append(pivot.buildInsertIntoPivotDeleteSQL(model));
    }
    for (ParameterDTO parameter : model.getParameters()) {
      for (ParameterTypeDefinitionDTO parameterTypeDefinition : parameter.getDefinitions()) {
        ParameterStrategy strategy =
            typeParameterStrategyFactory.getParameterStrategy(
                parameterTypeDefinition.getType().getName());

        if (strategy != null) {
          sb.append(
              strategy.appendTypeParameterAuditSQL(
                  model.getName(), parameter, parameterTypeDefinition, 2));
        }
        sb.append(buildRevInfoInsertSQL());
        sb.append(
            ParameterTablesBuilder.buildInsertParameterTypeDefinitionAuditSQL(
                parameter.getName(),
                model.getName(),
                parameterTypeDefinition.getType().getName(),
                2));
      }
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          ParameterTablesBuilder.buildInsertParameterAuditSQL(
              parameter.getName(), model.getName(), 2));
    }
    return sb.toString();
  }

  public String buildDeleteSQLParameterNotExist(
      ModelDTO dbModel, migration_files_creator.model.Model jsonModel) {
    StringBuilder sb = new StringBuilder();
    List<ParameterDTO> parametersForDeletion = new ArrayList<>(dbModel.getParameters());
    if (jsonModel.getName().equals(dbModel.getName())) {
      for (ParameterDTO parameter : dbModel.getParameters()) {
        for (HyperParameter hyperParameter : jsonModel.getHyperParameters()) {
          if (parameter.getName().equals(hyperParameter.getName())) {
            if (!compareParametersService.compareParameterColumns(hyperParameter, parameter)) {
              // Update parameter
              sb.append(updateParameterSQL(hyperParameter, jsonModel.getName()));
              sb.append(buildRevInfoInsertSQL());
              sb.append(
                  ParameterTablesBuilder.buildInsertParameterAuditSQL(
                      parameter.getName(), dbModel.getName(), 1));
              parametersForDeletion.remove(parameter);
            } else {
              // go one level deeper
              // Check if parameter type definitions are the same / delete if not
              List<ParameterTypeDistribution> parameterTypeDistributions =
                  InsertParametersTablesImpl.getParameterTypeDistributionList(hyperParameter);
              sb.append(
                  buildDeleteSQLParameterTypeDefinitionNotExist(
                      parameter.getDefinitions(),
                      parameterTypeDistributions,
                      dbModel.getName(),
                      parameter.getName()));
              parametersForDeletion.remove(parameter);
            }
          }
        }
      }
      // Delete parameter if it does not exist in the new model
      if (parametersForDeletion.size() > 0) {
        sb.append(buildDeleteSQLParameterNotExist(parametersForDeletion, dbModel));
      }
    }
    return sb.toString();
  }

  public String buildDeleteSQLParameterNotExist(
      List<ParameterDTO> parametersForDeletion, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    for (ParameterDTO parameter : parametersForDeletion) {
      // Add audit commands
      sb.append(addAUDCommandsIfParameterNotExist(parameter, model));
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          ParameterTablesBuilder.buildInsertParameterAuditSQLDelete(
              parameter.getName(), model.getName()));

      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeValueSql(
              parameter, "categorical_parameter_value", model));
      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeValueSql(
              parameter, "integer_parameter_value", model));
      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeValueSql(
              parameter, "float_parameter_range", model));
      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeSql(
              parameter, "categorical_parameter", model));
      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeSql(parameter, "integer_parameter", model));
      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeSql(parameter, "float_parameter", model));
      sb.append(
          DeleteTableBuilder.buildDeleteParameterTypeSql(parameter, "boolean_parameter", model));
      sb.append(DeleteTableBuilder.buildDeleteParameterTypeDefinitionSql(parameter, model));
      sb.append(buildDeleteParameterSQL(parameter, model));
    }
    return sb.toString();
  }

  public String addAUDCommandsIfParameterNotExist(ParameterDTO parameter, ModelDTO model) {
    StringBuilder sb = new StringBuilder();
    for (ParameterTypeDefinitionDTO parameterTypeDefinition : parameter.getDefinitions()) {
      ParameterStrategy strategy =
          typeParameterStrategyFactory.getParameterStrategy(
              parameterTypeDefinition.getType().getName());

      if (strategy != null) {
        sb.append(
            strategy.appendTypeParameterAuditSQL(
                model.getName(), parameter, parameterTypeDefinition, 2));
      }
      sb.append(buildRevInfoInsertSQL());
      sb.append(
          ParameterTablesBuilder.buildInsertParameterTypeDefinitionAuditSQL(
              parameter.getName(),
              model.getName(),
              parameterTypeDefinition.getType().getName(),
              2));
    }
    return sb.toString();
  }

  public String buildDeleteSQLParameterTypeDefinitionNotExist(
      Set<ParameterTypeDefinitionDTO> parameterTypeDefinitions,
      List<ParameterTypeDistribution> parameterTypeDistributions,
      String modelName,
      String parameterName) {
    StringBuilder sb = new StringBuilder();

    List<ParameterTypeDefinitionDTO> parameterTypeDefinitionForDeletion =
        findDefinitionsForDeletion(parameterTypeDefinitions, parameterTypeDistributions);

    appendDeleteSQLByStrategy(
        parameterTypeDefinitionForDeletion,
        sb,
        parameterTypeDistributions,
        modelName,
        parameterName);

    return sb.toString();
  }

  private List<ParameterTypeDefinitionDTO> findDefinitionsForDeletion(
      Set<ParameterTypeDefinitionDTO> parameterTypeDefinitions,
      List<ParameterTypeDistribution> parameterTypeDistributions) {

    return parameterTypeDefinitions.stream()
        .filter(
            definition ->
                parameterTypeDistributions.stream()
                    .noneMatch(distribution -> isMatching(definition, distribution)))
        .collect(Collectors.toList());
  }

  private boolean isMatching(
      ParameterTypeDefinitionDTO definition, ParameterTypeDistribution distribution) {
    return definition.getType().getName().equals(distribution.getParameterType())
        && definition.getDistribution().getName().equals(distribution.getParameterDistribution());
  }

  private void appendDeleteSQLByStrategy(
      List<ParameterTypeDefinitionDTO> definitions,
      StringBuilder sb,
      List<ParameterTypeDistribution> distributions,
      String modelName,
      String parameterName) {
    definitions.forEach(
        definition -> {
          distributions.forEach(
              distribution -> {
                Optional.ofNullable(
                        typeParameterStrategyFactory.getParameterStrategy(
                            definition.getType().getName()))
                    .ifPresent(
                        strategy ->
                            sb.append(
                                strategy.appendDeleteWithoutCheckTypeParameterSQL(
                                    definition, distribution, modelName, parameterName)));

                appendAdditionalSQL(definition, sb, modelName, parameterName);
              });
        });
  }

  private void appendAdditionalSQL(
      ParameterTypeDefinitionDTO definition,
      StringBuilder sb,
      String modelName,
      String parameterName) {
    sb.append(buildRevInfoInsertSQL());
    sb.append(
        ParameterTablesBuilder.buildInsertParameterTypeDefinitionAuditSQL(
            parameterName, modelName, definition.getType().getName(), 2));
    sb.append(
        DeleteTableBuilder.buildDeleteParameterTypeDefinitionSql(
            definition, modelName, parameterName));
  }
}
