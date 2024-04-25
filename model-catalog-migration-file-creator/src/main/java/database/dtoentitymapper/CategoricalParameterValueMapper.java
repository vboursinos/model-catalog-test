package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.CategoricalParameterValueDTO;
import database.entity.CategoricalParameterValue;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoricalParameterValueMapper
    extends MapperInterface<CategoricalParameterValueDTO, CategoricalParameterValue> {}
