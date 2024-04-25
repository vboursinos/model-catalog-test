package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.FloatParameterRangeDTO;
import database.entity.FloatParameterRange;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FloatParameterRangeMapper
    extends MapperInterface<FloatParameterRangeDTO, FloatParameterRange> {}
