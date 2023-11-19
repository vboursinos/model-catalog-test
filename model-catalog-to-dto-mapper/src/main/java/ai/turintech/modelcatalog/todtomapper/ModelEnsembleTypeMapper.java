package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.to.ModelEnsembleTypeTO;
import ai.turintech.modelcatalog.to.ModelTO;
import java.util.List;
import org.mapstruct.*;

/** Mapper for the entity {@link ModelEnsembleType} and its DTO {@link ModelEnsembleTypeDTO}. */
@Mapper(componentModel = "spring", implementationName = "ModelEnsembleTypeMapperTOImpl")
public interface ModelEnsembleTypeMapper
    extends MapperInterface<ModelEnsembleTypeTO, ModelEnsembleTypeDTO> {
  List<ModelEnsembleTypeDTO> toDTO(List<ModelEnsembleTypeTO> s);

  List<ModelEnsembleTypeTO> toTO(List<ModelEnsembleTypeDTO> s);

  @Named("modelId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelDTO toDtoModelId(ModelTO model);
}
