package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.*;
import ai.turintech.modelcatalog.entity.*;
import org.mapstruct.Mapper;

/** Mapper for the entity {@link Model} and its DTO {@link ModelDTO}. */
@Mapper(componentModel = "spring")
public interface ModelLimitedMapper extends MapperInterface<ModelDTO, ModelLimited> {}
