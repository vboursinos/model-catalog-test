package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameterValue} and its DTO {@link
 * CategoricalParameterValueDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "CategoricalParameterValueMapperTOImpl")
public interface CategoricalParameterValueMapper
    extends EntityMapper<CategoricalParameterValueTO, CategoricalParameterValueDTO> {}
