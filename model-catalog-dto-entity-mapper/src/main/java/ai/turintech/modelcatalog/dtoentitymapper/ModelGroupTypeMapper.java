package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.ModelGroupType;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelGroupType} and its DTO {@link ModelGroupTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelGroupTypeMapper extends EntityMapper<ModelGroupTypeDTO, ModelGroupType> {}
