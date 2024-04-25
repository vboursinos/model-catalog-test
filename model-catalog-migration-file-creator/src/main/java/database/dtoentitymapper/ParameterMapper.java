package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ParameterDTO;
import database.entity.Parameter;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ParameterMapper extends MapperInterface<ParameterDTO, Parameter> {}
