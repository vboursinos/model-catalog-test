package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelType} and its DTO {@link ModelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelTypeMapper extends EntityMapper<ModelTypeDTO, ModelType> {}
