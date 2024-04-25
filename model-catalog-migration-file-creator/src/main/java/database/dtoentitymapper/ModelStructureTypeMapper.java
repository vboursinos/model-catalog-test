package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ModelStructureTypeDTO;
import database.entity.ModelStructureType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ModelStructureTypeMapper
    extends MapperInterface<ModelStructureTypeDTO, ModelStructureType> {}
