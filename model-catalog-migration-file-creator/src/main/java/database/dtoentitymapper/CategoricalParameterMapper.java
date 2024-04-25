package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.CategoricalParameterDTO;
import database.entity.CategoricalParameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoricalParameterMapper
    extends MapperInterface<CategoricalParameterDTO, CategoricalParameter> {}
