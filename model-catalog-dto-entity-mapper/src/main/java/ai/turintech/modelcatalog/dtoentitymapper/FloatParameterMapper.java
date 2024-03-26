package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import org.mapstruct.Mapper;

/** Mapper for the entity {@link FloatParameter} and its DTO {@link FloatParameterDTO}. */
@Mapper(componentModel = "spring")
public interface FloatParameterMapper extends MapperInterface<FloatParameterDTO, FloatParameter> {}
