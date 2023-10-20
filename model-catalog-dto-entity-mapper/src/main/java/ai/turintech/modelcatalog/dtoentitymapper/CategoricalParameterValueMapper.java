package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameterValue} and its DTO {@link CategoricalParameterValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoricalParameterValueMapper extends EntityMapper<CategoricalParameterValueDTO, CategoricalParameterValue> {}
