package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FloatParameterRange} and its DTO {@link FloatParameterRangeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloatParameterRangeMapper extends EntityMapper<FloatParameterRangeDTO, FloatParameterRange> {
    FloatParameterRangeDTO toDto(FloatParameterRange s);

    @Named("floatParameterId")
    @BeanMapping(ignoreByDefault = true)
    FloatParameterDTO toDtoFloatParameterId(FloatParameter floatParameter);
}
