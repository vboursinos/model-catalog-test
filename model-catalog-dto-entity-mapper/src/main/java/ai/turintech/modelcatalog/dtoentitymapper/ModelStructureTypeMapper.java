package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import org.mapstruct.*;

/** Mapper for the entity {@link ModelStructureType} and its DTO {@link ModelStructureTypeDTO}. */
@Mapper(componentModel = "spring")
public interface ModelStructureTypeMapper
    extends MapperInterface<ModelStructureTypeDTO, ModelStructureType> {}
