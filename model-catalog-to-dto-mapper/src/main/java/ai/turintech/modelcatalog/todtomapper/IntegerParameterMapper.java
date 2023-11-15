package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.to.IntegerParameterTO;
import org.mapstruct.*;

/** Mapper for the entity {@link IntegerParameter} and its DTO {@link IntegerParameterDTO}. */
@Mapper(componentModel = "spring", implementationName = "IntegerParameterMapperTOImpl")
public interface IntegerParameterMapper
    extends EntityMapper<IntegerParameterTO, IntegerParameterDTO> {}
