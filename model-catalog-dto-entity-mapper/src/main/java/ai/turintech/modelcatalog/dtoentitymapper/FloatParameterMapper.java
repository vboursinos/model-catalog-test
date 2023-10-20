package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FloatParameter} and its DTO {@link FloatParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloatParameterMapper extends EntityMapper<FloatParameterDTO, FloatParameter> {}
