package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/** Mapper for the entity {@link IntegerParameter} and its DTO {@link IntegerParameterDTO}. */
@Mapper(componentModel = "spring")
public interface IntegerParameterMapper
    extends EntityMapper<IntegerParameterDTO, IntegerParameter> {
  @Mapping(target = "defaultValue", source = "defaultValue")
  IntegerParameterDTO toDto(IntegerParameter s);

  @Named("parameterTypeDefinitionId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ParameterTypeDefinitionDTO toDtoParameterTypeDefinitionId(
      ParameterTypeDefinition parameterTypeDefinition);
}
