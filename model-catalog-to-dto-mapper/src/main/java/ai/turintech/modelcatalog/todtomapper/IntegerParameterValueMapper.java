package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.IntegerParameterValueTO;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegerParameterValue} and its DTO {@link IntegerParameterValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegerParameterValueMapper extends EntityMapper<IntegerParameterValueTO, IntegerParameterValueDTO> {}
