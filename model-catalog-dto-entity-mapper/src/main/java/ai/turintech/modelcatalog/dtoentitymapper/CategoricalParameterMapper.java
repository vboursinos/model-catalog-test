package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
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
  default void afterMappingFrom(
      CategoricalParameter categoricalParameter,
      @MappingTarget CategoricalParameterDTO categoricalParameterDTO) {
    categoricalParameterDTO.setId(categoricalParameter.getId());
  }

  @AfterMapping
  default void afterMappingTo(
      CategoricalParameterDTO categoricalParameterDTO,
      @MappingTarget CategoricalParameter categoricalParameter) {
    categoricalParameter.setId(categoricalParameterDTO.getId());
  }
}
