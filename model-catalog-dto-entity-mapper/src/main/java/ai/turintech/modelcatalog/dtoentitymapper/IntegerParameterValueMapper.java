package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegerParameterValue} and its DTO {@link IntegerParameterValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegerParameterValueMapper
    extends AbstractMapper<IntegerParameterValueDTO, IntegerParameterValue>{}
