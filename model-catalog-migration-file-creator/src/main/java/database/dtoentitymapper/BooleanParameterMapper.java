package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.BooleanParameterDTO;
import database.entity.BooleanParameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BooleanParameterMapper
    extends MapperInterface<BooleanParameterDTO, BooleanParameter> {}
