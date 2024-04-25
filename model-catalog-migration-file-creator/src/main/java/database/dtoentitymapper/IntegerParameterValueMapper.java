package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.IntegerParameterValueDTO;
import database.entity.IntegerParameterValue;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IntegerParameterValueMapper
    extends MapperInterface<IntegerParameterValueDTO, IntegerParameterValue> {}
