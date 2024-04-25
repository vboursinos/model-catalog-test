package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ParameterTypeDefinitionDTO;
import database.entity.ParameterTypeDefinition;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ParameterTypeDefinitionMapper
    extends MapperInterface<ParameterTypeDefinitionDTO, ParameterTypeDefinition> {}
