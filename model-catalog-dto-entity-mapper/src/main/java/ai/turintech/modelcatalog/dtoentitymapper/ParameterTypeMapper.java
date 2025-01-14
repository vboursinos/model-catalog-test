package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import org.mapstruct.*;

/** Mapper for the entity {@link ParameterType} and its DTO {@link ParameterTypeDTO}. */
@Mapper(componentModel = "spring")
public interface ParameterTypeMapper extends MapperInterface<ParameterTypeDTO, ParameterType> {}
