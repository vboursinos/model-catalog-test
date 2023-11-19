package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.entity.Parameter;
import org.mapstruct.*;

/** Mapper for the entity {@link Parameter} and its DTO {@link ParameterDTO}. */
@Mapper(componentModel = "spring")
public interface ParameterMapper extends MapperInterface<ParameterDTO, Parameter> {}
