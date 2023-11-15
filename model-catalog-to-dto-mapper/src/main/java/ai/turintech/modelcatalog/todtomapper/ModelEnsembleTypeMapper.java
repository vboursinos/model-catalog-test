package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.to.ModelEnsembleTypeTO;
import ai.turintech.modelcatalog.to.ModelTO;
import org.mapstruct.*;

/** Mapper for the entity {@link ModelEnsembleType} and its DTO {@link ModelEnsembleTypeDTO}. */
@Mapper(componentModel = "spring", implementationName = "ModelEnsembleTypeMapperTOImpl")
public interface ModelEnsembleTypeMapper
    extends EntityMapper<ModelEnsembleTypeTO, ModelEnsembleTypeDTO> {
  ModelEnsembleTypeDTO toDto(ModelEnsembleTypeTO s);

  @Named("modelId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelDTO toDtoModelId(ModelTO model);
}
