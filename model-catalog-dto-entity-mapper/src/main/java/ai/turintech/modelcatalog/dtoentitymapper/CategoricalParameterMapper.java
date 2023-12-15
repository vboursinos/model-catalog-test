package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
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
  default void afterMapping(
      @MappingTarget CategoricalParameter categoricalParameter,
      CategoricalParameterDTO categoricalParameterDTO) {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(categoricalParameterDTO.getParameterTypeDefinitionId());
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);
  }
}
