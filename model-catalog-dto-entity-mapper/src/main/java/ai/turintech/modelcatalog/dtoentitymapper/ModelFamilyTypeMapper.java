package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelFamilyType} and its DTO {@link ModelFamilyTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelFamilyTypeMapper extends EntityMapper<ModelFamilyTypeDTO, ModelFamilyType> {
    @Mapping(target = "models", source = "models", qualifiedByName = "modelId")
    ModelFamilyTypeDTO toDto(ModelFamilyType s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);
}
