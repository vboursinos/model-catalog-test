package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ParameterTypeDTO;
import database.entity.ParameterType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ParameterTypeMapper extends MapperInterface<ParameterTypeDTO, ParameterType> {}
