package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.ModelType;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelType} and its DTO {@link ModelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelTypeMapper extends EntityMapper<ModelTypeDTO, ModelType> {
    @Mapping(target = "models", source = "models", qualifiedByName = "modelId")
    ModelTypeDTO toDto(ModelType s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);
}
