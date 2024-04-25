package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ModelGroupTypeDTO;
import database.entity.ModelGroupType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ModelGroupTypeMapper extends MapperInterface<ModelGroupTypeDTO, ModelGroupType> {}
