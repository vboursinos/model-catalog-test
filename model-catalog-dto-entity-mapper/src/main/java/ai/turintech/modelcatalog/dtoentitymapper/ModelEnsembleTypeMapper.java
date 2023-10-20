package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelEnsembleType} and its DTO {@link ModelEnsembleTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelEnsembleTypeMapper extends EntityMapper<ModelEnsembleTypeDTO, ModelEnsembleType> {
    @Mapping(target = "models", source = "models", qualifiedByName = "modelId")
    ModelEnsembleTypeDTO toDto(ModelEnsembleType s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);
}
