package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.to.ModelTypeTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "ModelTypeMapperTOImpl")
public interface ModelTypeMapper extends EntityMapper<ModelTypeTO, ModelTypeDTO> {
  //    ModelTypeDTO toDto(ModelTypeTO s);
  //
  //    ModelTypeTO toTO(ModelTypeDTO dto);
  //
  //    List<ModelTypeTO> toTO(List<ModelTypeDTO> s);
}
