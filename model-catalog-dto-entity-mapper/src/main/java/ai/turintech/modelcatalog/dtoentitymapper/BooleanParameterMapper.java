package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BooleanParameter} and its DTO {@link BooleanParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface BooleanParameterMapper extends EntityMapper<BooleanParameterDTO, BooleanParameter> {}
