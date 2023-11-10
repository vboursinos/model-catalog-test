package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.CategoricalParameterTO;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameter} and its DTO {@link CategoricalParameterDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "CategoricalParameterMapperTOImpl")
public interface CategoricalParameterMapper extends EntityMapper<CategoricalParameterTO, CategoricalParameterDTO> {}
