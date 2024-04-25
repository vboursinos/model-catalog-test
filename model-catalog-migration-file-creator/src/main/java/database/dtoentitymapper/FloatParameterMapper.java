package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.FloatParameterDTO;
import database.entity.FloatParameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FloatParameterMapper extends MapperInterface<FloatParameterDTO, FloatParameter> {}
