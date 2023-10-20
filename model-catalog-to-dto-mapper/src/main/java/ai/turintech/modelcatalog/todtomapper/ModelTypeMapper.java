package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.ModelTO;
import ai.turintech.modelcatalog.to.ModelTypeTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelType} and its DTO {@link ModelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelTypeMapper extends EntityMapper<ModelTypeTO, ModelTypeDTO> {
    @Mapping(target = "models", source = "models", qualifiedByName = "modelId")
    ModelTypeDTO toDto(ModelTypeTO s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(ModelTO model);
}
