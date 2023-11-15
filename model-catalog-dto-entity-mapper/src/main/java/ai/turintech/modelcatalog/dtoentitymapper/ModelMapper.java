package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.entity.*;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/** Mapper for the entity {@link Model} and its DTO {@link ModelDTO}. */
@Mapper(componentModel = "spring")
public interface ModelMapper extends EntityMapper<ModelDTO, Model> {
  @Mapping(target = "groups", source = "groups")
  @Mapping(target = "incompatibleMetrics", source = "incompatibleMetrics")
  @Mapping(target = "mlTask", source = "mlTask")
  @Mapping(target = "structure", source = "structure")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "familyType", source = "familyType")
  @Mapping(target = "ensembleType", source = "ensembleType")
  ModelDTO toDto(Model s);

  @Mapping(target = "removeGroups", ignore = true)
  @Mapping(target = "removeIncompatibleMetrics", ignore = true)
  Model toEntity(ModelDTO modelDTO);

  @Named("modelGroupTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelGroupTypeDTO toDtoModelGroupTypeId(ModelGroupType modelGroupType);

  @Named("modelGroupTypeIdSet")
  default Set<ModelGroupTypeDTO> toDtoModelGroupTypeIdSet(Set<ModelGroupType> modelGroupType) {
    return modelGroupType.stream().map(this::toDtoModelGroupTypeId).collect(Collectors.toSet());
  }

  @Named("metricId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  MetricDTO toDtoMetricId(Metric metric);

  @Named("metricIdSet")
  default Set<MetricDTO> toDtoMetricIdSet(Set<Metric> metric) {
    return metric.stream().map(this::toDtoMetricId).collect(Collectors.toSet());
  }

  @Named("mlTaskTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  MlTaskTypeDTO toDtoMlTaskTypeId(MlTaskType mlTaskType);

  @Named("modelStructureTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelStructureTypeDTO toDtoModelStructureTypeId(ModelStructureType modelStructureType);

  @Named("modelTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelTypeDTO toDtoModelTypeId(ModelType modelType);

  @Named("modelFamilyTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelFamilyTypeDTO toDtoModelFamilyTypeId(ModelFamilyType modelFamilyType);

  @Named("modelEnsembleTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelEnsembleTypeDTO toDtoModelEnsembleTypeId(ModelEnsembleType modelEnsembleType);
}
