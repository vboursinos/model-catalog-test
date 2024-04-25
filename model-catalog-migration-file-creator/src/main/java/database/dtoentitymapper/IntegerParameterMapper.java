package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.IntegerParameterDTO;
import database.entity.IntegerParameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IntegerParameterMapper
    extends MapperInterface<IntegerParameterDTO, IntegerParameter> {}
