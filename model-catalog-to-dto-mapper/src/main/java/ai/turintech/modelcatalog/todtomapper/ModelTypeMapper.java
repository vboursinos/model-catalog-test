package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.to.ModelTypeTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", implementationName = "ModelTypeMapperTOImpl")
public interface ModelTypeMapper extends MapperInterface<ModelTypeTO, ModelTypeDTO> {

    List<ModelTypeDTO> toDto(List<ModelTypeTO> s);

    List<ModelTypeTO> toTO(List<ModelTypeDTO> s);
}
