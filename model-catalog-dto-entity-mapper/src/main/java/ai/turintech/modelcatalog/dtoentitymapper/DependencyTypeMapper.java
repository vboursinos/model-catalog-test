package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.DependencyTypeDTO;
import ai.turintech.modelcatalog.entity.DependencyType;
import org.mapstruct.Mapper;

/** Mapper for the entity {@link DependencyType} and its DTO {@link DependencyTypeDTO}. */
@Mapper(componentModel = "spring")
public interface DependencyTypeMapper extends MapperInterface<DependencyTypeDTO, DependencyType> {}
