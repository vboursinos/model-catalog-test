package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameter} and its DTO {@link CategoricalParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoricalParameterMapper extends EntityMapper<CategoricalParameterDTO, CategoricalParameter> {}
