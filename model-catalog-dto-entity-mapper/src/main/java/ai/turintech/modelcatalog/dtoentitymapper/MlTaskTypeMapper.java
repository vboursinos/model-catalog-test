package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link MlTaskType} and its DTO {@link MlTaskTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface MlTaskTypeMapper extends EntityMapper<MlTaskTypeDTO, MlTaskType> {}
