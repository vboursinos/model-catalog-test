package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.to.MetricTO;
import ai.turintech.modelcatalog.to.ModelGroupTypeTO;
import ai.turintech.modelcatalog.to.ModelTO;
import ai.turintech.modelcatalog.to.ParameterTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/** Mapper for the entity {@link Model} and its DTO {@link ModelDTO}. */
@Mapper(componentModel = "spring", implementationName = "ModelMapperTOImpl")
public interface ModelMapper extends MapperInterface<ModelTO, ModelDTO> {
  @Mapping(target = "groups", source = "groups", qualifiedByName = "modelGroupTypeIdSet")
  @Mapping(
      target = "incompatibleMetrics",
      source = "incompatibleMetrics",
      qualifiedByName = "metricIdSet")
  @Mapping(target = "parameters", source = "parameters", qualifiedByName = "parameterId")
  ModelDTO to(ModelTO s);

  ModelTO from(ModelDTO modelDTO);

  @Named("modelGroupTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelGroupTypeDTO toDtoModelGroupTypeId(ModelGroupTypeTO modelGroupType);

  @Named("modelGroupTypeIdSet")
  default Set<ModelGroupTypeDTO> toDtoModelGroupTypeIdSet(Set<ModelGroupTypeTO> modelGroupType) {
    return modelGroupType.stream().map(this::toDtoModelGroupTypeId).collect(Collectors.toSet());
  }

  @Named("metricId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  MetricDTO toDtoMetricId(MetricTO metric);

  @Named("metricIdSet")
  default Set<MetricDTO> toDtoMetricIdSet(Set<MetricTO> metric) {
    return metric.stream().map(this::toDtoMetricId).collect(Collectors.toSet());
  }

  @Named("parameterId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ParameterDTO toDtoParameterId(ParameterTO parameter);
}
