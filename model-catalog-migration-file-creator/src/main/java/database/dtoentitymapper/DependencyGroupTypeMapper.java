package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.DependencyGroupTypeDTO;
import database.entity.DependencyGroupType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DependencyGroupTypeMapper
    extends MapperInterface<DependencyGroupTypeDTO, DependencyGroupType> {}
