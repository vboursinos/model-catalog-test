package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterTypeDefinition} and its DTO {@link
 * ParameterTypeDefinitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterTypeDefinitionMapper
    extends EntityMapper<ParameterTypeDefinitionDTO, ParameterTypeDefinition> {
  @Mapping(target = "distribution", source = "distribution")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "categoricalParameter", source = "categoricalParameter")
  @Mapping(target = "booleanParameter", source = "booleanParameter")
  @Mapping(target = "floatParameter", source = "floatParameter")
  @Mapping(target = "integerParameter", source = "integerParameter")
  ParameterTypeDefinitionDTO toDto(ParameterTypeDefinition s);

  @Named("parameterDistributionTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ParameterDistributionTypeDTO toDtoParameterDistributionTypeId(
      ParameterDistributionType parameterDistributionType);

  @Named("parameterId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ParameterDTO toDtoParameterId(Parameter parameter);

  @Named("parameterTypeId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ParameterTypeDTO toDtoParameterTypeId(ParameterType parameterType);
}
