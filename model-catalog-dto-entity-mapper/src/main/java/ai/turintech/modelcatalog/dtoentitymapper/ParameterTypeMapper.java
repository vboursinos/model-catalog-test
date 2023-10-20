package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterType} and its DTO {@link ParameterTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterTypeMapper extends EntityMapper<ParameterTypeDTO, ParameterType> {
    @Mapping(target = "parameter", source = "parameter", qualifiedByName = "parameterId")
    @Mapping(target = "parameterTypeDefinition", source = "parameterTypeDefinition", qualifiedByName = "parameterTypeDefinitionId")
    ParameterTypeDTO toDto(ParameterType s);

    @Named("parameterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterDTO toDtoParameterId(Parameter parameter);

    @Named("parameterTypeDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterTypeDefinitionDTO toDtoParameterTypeDefinitionId(ParameterTypeDefinition parameterTypeDefinition);
}
