package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.entity.Parameter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity {@link CategoricalParameter} and its DTO {@link CategoricalParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoricalParameterMapper
    extends MapperInterface<CategoricalParameterDTO, CategoricalParameter> {

  @AfterMapping
  default void addParameter(
      @MappingTarget CategoricalParameter target, CategoricalParameterDTO source) {
    Parameter parameter = new Parameter();
    parameter.setId(source.getParameterId());
    target.setParameter(parameter);
  }
}
