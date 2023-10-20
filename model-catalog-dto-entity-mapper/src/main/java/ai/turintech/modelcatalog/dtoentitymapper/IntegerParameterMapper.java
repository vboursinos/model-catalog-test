package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegerParameter} and its DTO {@link IntegerParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegerParameterMapper extends EntityMapper<IntegerParameterDTO, IntegerParameter> {}
