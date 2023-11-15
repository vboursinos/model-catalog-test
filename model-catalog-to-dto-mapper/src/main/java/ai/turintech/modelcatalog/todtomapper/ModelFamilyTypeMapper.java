package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.to.ModelFamilyTypeTO;
import ai.turintech.modelcatalog.to.ModelTO;
import org.mapstruct.*;

/** Mapper for the entity {@link ModelFamilyType} and its DTO {@link ModelFamilyTypeDTO}. */
@Mapper(componentModel = "spring", implementationName = "ModelFamilyTypeMapperTOImpl")
public interface ModelFamilyTypeMapper extends EntityMapper<ModelFamilyTypeTO, ModelFamilyTypeDTO> {
  ModelFamilyTypeDTO toDto(ModelFamilyTypeTO s);

  @Named("modelId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ModelDTO toDtoModelId(ModelTO model);
}
