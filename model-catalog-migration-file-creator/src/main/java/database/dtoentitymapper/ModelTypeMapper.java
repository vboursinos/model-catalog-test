package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ModelTypeDTO;
import database.entity.ModelType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ModelTypeMapper extends MapperInterface<ModelTypeDTO, ModelType> {}
