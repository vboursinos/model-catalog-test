package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.ParameterTO;
import ai.turintech.modelcatalog.to.ParameterTypeTO;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterType} and its DTO {@link ParameterTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterTypeMapper extends EntityMapper<ParameterTypeTO, ParameterTypeDTO> {
    @Mapping(target = "parameter", source = "parameter", qualifiedByName = "parameterId")
    @Mapping(target = "parameterTypeDefinition", source = "parameterTypeDefinition", qualifiedByName = "parameterTypeDefinitionId")
    ParameterTypeDTO toDto(ParameterTypeTO s);

    @Named("parameterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterDTO toDtoParameterId(ParameterTO parameter);

    @Named("parameterTypeDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterTypeDefinitionDTO toDtoParameterTypeDefinitionId(ParameterTypeDefinitionTO parameterTypeDefinition);
}
