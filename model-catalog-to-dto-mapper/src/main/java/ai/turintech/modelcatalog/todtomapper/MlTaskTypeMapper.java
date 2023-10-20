package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.MlTaskTypeTO;
import ai.turintech.modelcatalog.to.ModelTO;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MlTaskType} and its DTO {@link MlTaskTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface MlTaskTypeMapper extends EntityMapper<MlTaskTypeTO, MlTaskTypeDTO> {
    @Mapping(target = "models", source = "models", qualifiedByName = "modelId")
    MlTaskTypeDTO toDto(MlTaskTypeTO s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(ModelTO model);
}
