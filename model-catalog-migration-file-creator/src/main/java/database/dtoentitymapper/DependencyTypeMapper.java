package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.DependencyTypeDTO;
import database.entity.DependencyType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DependencyTypeMapper extends MapperInterface<DependencyTypeDTO, DependencyType> {}
