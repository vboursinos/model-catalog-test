package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.to.FloatParameterRangeTO;
import org.mapstruct.*;

/** Mapper for the entity {@link FloatParameterRange} and its DTO {@link FloatParameterRangeDTO}. */
@Mapper(componentModel = "spring", implementationName = "FloatParameterRangeMapperTOImpl")
public interface FloatParameterRangeMapper
    extends EntityMapper<FloatParameterRangeTO, FloatParameterRangeDTO> {}
