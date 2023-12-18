package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import org.mapstruct.*;

/** Mapper for the entity {@link BooleanParameter} and its DTO {@link BooleanParameterDTO}. */
@Mapper(componentModel = "spring")
public interface BooleanParameterMapper
    extends MapperInterface<BooleanParameterDTO, BooleanParameter> {

  @AfterMapping
  default void afterMappingFrom(
      BooleanParameter booleanParameter, @MappingTarget BooleanParameterDTO booleanParameterDTO) {
    booleanParameterDTO.setId(booleanParameter.getParameterTypeDefinitionId());
  }

  @AfterMapping
  default void afterMappingTo(
      BooleanParameterDTO booleanParameterDTO, @MappingTarget BooleanParameter booleanParameter) {
    booleanParameter.setParameterTypeDefinitionId(booleanParameterDTO.getId());
  }
}
