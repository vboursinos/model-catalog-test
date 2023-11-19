package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterTypeDefinition} and its DTO {@link
 * ParameterTypeDefinitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterTypeDefinitionMapper
    extends MapperInterface<ParameterTypeDefinitionDTO, ParameterTypeDefinition> {}
