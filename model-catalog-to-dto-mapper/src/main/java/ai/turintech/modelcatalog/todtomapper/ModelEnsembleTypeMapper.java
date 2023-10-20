package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.ModelTO;
import ai.turintech.modelcatalog.to.ModelEnsembleTypeTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelEnsembleType} and its DTO {@link ModelEnsembleTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelEnsembleTypeMapper extends EntityMapper<ModelEnsembleTypeTO, ModelEnsembleTypeDTO> {
    @Mapping(target = "models", source = "models", qualifiedByName = "modelId")
    ModelEnsembleTypeDTO toDto(ModelEnsembleTypeTO s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(ModelTO model);
}
