package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.to.ModelFamilyTypeTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity {@link ModelFamilyType} and its DTO {@link ModelFamilyTypeDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "ModelFamilyTypeMapperTOImpl")
public interface ModelFamilyTypeMapper extends MapperInterface<ModelFamilyTypeTO, ModelFamilyTypeDTO> {
    List<ModelFamilyTypeDTO> toDto(List<ModelFamilyTypeTO> s);

    List<ModelFamilyTypeTO> toTO(List<ModelFamilyTypeDTO> s);
}
