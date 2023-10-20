package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.FloatParameterRange;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FloatParameterRange} and its DTO {@link FloatParameterRangeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloatParameterRangeMapper extends EntityMapper<FloatParameterRangeDTO, FloatParameterRange> {}
