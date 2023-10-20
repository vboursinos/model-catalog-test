package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameterValue} and its DTO {@link CategoricalParameterValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoricalParameterValueMapper extends EntityMapper<CategoricalParameterValueTO, CategoricalParameterValueDTO> {}
