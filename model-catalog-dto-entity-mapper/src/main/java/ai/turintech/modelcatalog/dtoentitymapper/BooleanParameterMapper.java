package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.entity.Parameter;
import org.mapstruct.*;

/** Mapper for the entity {@link BooleanParameter} and its DTO {@link BooleanParameterDTO}. */
@Mapper(componentModel = "spring")
public interface BooleanParameterMapper
    extends MapperInterface<BooleanParameterDTO, BooleanParameter> {

  @AfterMapping
  default void addParameter(@MappingTarget BooleanParameter target, BooleanParameterDTO source) {
    Parameter parameter = new Parameter();
    parameter.setId(source.getParameterId());
    target.setParameter(parameter);
  }
}
