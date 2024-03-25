package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.DependencyGroupTypeDTO;
import ai.turintech.modelcatalog.entity.DependencyGroupType;
import org.mapstruct.Mapper;

/** Mapper for the entity {@link DependencyGroupType} and its DTO {@link DependencyGroupTypeDTO}. */
@Mapper(componentModel = "spring")
public interface DependencyGroupTypeMapper
    extends MapperInterface<DependencyGroupTypeDTO, DependencyGroupType> {}
