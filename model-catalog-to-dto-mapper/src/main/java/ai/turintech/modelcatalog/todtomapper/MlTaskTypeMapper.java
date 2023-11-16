package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.to.MlTaskTypeTO;
import ai.turintech.modelcatalog.to.ModelTO;
import org.mapstruct.*;

import java.util.List;

/** Mapper for the entity {@link MlTaskType} and its DTO {@link MlTaskTypeDTO}. */
@Mapper(
    componentModel = "spring",
    implementationName = "MlTaskTypeMapperTOImpl",
    uses = {ModelMapper.class})
public interface MlTaskTypeMapper extends MapperInterface<MlTaskTypeTO, MlTaskTypeDTO> {

  List<MlTaskTypeDTO> toDTO(List<MlTaskTypeTO> s);

  List<MlTaskTypeTO> toTO(List<MlTaskTypeDTO> s);

  @Named("modelId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelDTO toDtoModelId(ModelTO model);
}
