package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.MlTaskTypeDTO;
import database.entity.MlTaskType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MlTaskTypeMapper extends MapperInterface<MlTaskTypeDTO, MlTaskType> {}
