package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.to.BooleanParameterTO;
import org.mapstruct.*;

/** Mapper for the entity {@link BooleanParameter} and its DTO {@link BooleanParameterDTO}. */
@Mapper(componentModel = "spring", implementationName = "BooleanParameterMapperTOImpl")
public interface BooleanParameterMapper
    extends EntityMapper<BooleanParameterTO, BooleanParameterDTO> {}
