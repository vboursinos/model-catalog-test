package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.FloatParameterTO;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FloatParameter} and its DTO {@link FloatParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloatParameterMapper extends EntityMapper<FloatParameterTO, FloatParameterDTO> {}
