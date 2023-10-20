package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.BooleanParameterTO;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BooleanParameter} and its DTO {@link BooleanParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface BooleanParameterMapper extends EntityMapper<BooleanParameterTO, BooleanParameterDTO> {}
