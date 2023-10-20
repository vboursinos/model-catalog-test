package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.MlTaskType;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MlTaskType} and its DTO {@link MlTaskTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface MlTaskTypeMapper extends EntityMapper<MlTaskTypeDTO, MlTaskType> {
    @Mapping(target = "models", source = "models", qualifiedByName = "modelId")
    MlTaskTypeDTO toDto(MlTaskType s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);
}
