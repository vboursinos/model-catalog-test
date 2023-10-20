package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.ModelTO;
import ai.turintech.modelcatalog.to.ModelStructureTypeTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelStructureType} and its DTO {@link ModelStructureTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelStructureTypeMapper extends EntityMapper<ModelStructureTypeTO, ModelStructureTypeDTO> {
    @Mapping(target = "model", source = "model", qualifiedByName = "modelId")
    ModelStructureTypeDTO toDto(ModelStructureTypeTO s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(ModelTO model);
}
