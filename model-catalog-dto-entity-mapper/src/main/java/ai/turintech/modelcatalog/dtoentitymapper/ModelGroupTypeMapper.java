package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import org.mapstruct.*;

/** Mapper for the entity {@link ModelGroupType} and its DTO {@link ModelGroupTypeDTO}. */
@Mapper(componentModel = "spring")
public interface ModelGroupTypeMapper extends AbstractMapper<ModelGroupTypeDTO,ModelGroupType> {}
