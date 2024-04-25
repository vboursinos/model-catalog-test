package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ModelFamilyTypeDTO;
import database.entity.ModelFamilyType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ModelFamilyTypeMapper
    extends MapperInterface<ModelFamilyTypeDTO, ModelFamilyType> {}
