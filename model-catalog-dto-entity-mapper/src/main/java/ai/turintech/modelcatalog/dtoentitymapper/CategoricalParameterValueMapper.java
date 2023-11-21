package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameterValue} and its DTO {@link
 * CategoricalParameterValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoricalParameterValueMapper
    extends MapperInterface<CategoricalParameterValueDTO, CategoricalParameterValue> {}
