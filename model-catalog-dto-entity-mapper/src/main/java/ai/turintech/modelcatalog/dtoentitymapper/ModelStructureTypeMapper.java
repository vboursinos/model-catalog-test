package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelStructureType} and its DTO {@link ModelStructureTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelStructureTypeMapper extends EntityMapper<ModelStructureTypeDTO, ModelStructureType> {
    @Mapping(target = "model", source = "model", qualifiedByName = "modelId")
    ModelStructureTypeDTO toDto(ModelStructureType s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);
}
