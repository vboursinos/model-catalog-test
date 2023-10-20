package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.ParameterTO;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parameter} and its DTO {@link ParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterMapper extends EntityMapper<ParameterTO, ParameterDTO> {
    @Mapping(target = "definitions", source = "definitions", qualifiedByName = "parameterTypeDefinitionId")
    ParameterDTO toDto(ParameterTO s);

    @Named("parameterTypeDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterTypeDefinitionDTO toDtoParameterTypeDefinitionId(ParameterTypeDefinitionTO parameterTypeDefinition);
}
