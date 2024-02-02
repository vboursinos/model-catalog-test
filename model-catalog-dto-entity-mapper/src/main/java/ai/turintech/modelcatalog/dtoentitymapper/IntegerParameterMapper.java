package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.entity.Parameter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/** Mapper for the entity {@link IntegerParameter} and its DTO {@link IntegerParameterDTO}. */
@Mapper(componentModel = "spring")
public interface IntegerParameterMapper
    extends MapperInterface<IntegerParameterDTO, IntegerParameter> {
  @AfterMapping
  default void addParameter(@MappingTarget IntegerParameter target, IntegerParameterDTO source) {
    Parameter parameter = new Parameter();
    parameter.setId(source.getParameterId());
    target.setParameter(parameter);
  }
}
