package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegerParameterValue} and its DTO {@link IntegerParameterValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegerParameterValueMapper extends EntityMapper<IntegerParameterValueDTO, IntegerParameterValue> {}
