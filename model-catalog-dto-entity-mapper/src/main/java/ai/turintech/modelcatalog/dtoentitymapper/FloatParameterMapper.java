package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link FloatParameter} and its DTO {@link FloatParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloatParameterMapper extends EntityMapper<FloatParameterDTO, FloatParameter> {

    @Mapping(target = "defaultValue", source = "defaultValue")
    FloatParameterDTO toDto(FloatParameter s);

    @Named("parameterTypeDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterTypeDefinitionDTO toDtoParameterTypeDefinitionId(ParameterTypeDefinition parameterTypeDefinition);
}
