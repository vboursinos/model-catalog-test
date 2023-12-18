package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/** Mapper for the entity {@link IntegerParameter} and its DTO {@link IntegerParameterDTO}. */
@Mapper(componentModel = "spring")
public interface IntegerParameterMapper
    extends MapperInterface<IntegerParameterDTO, IntegerParameter> {

  @AfterMapping
  default void afterMappingFrom(
      IntegerParameter integerParameter, @MappingTarget IntegerParameterDTO integerParameterDTO) {
    integerParameterDTO.setId(integerParameter.getParameterTypeDefinitionId());
  }

  @AfterMapping
  default void afterMappingTo(
      IntegerParameterDTO integerParameterDTO, @MappingTarget IntegerParameter integerParameter) {
    integerParameter.setParameterTypeDefinitionId(integerParameterDTO.getId());
  }
}
