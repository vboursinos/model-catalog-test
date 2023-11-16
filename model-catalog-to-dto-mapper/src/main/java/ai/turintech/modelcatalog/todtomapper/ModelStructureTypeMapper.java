package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.to.ModelStructureTypeTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity {@link ModelStructureType} and its DTO {@link ModelStructureTypeDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "ModelStructureTypeMapperTOImpl")
public interface ModelStructureTypeMapper
        extends MapperInterface<ModelStructureTypeTO, ModelStructureTypeDTO> {

    List<ModelStructureTypeDTO> toDto(List<ModelStructureTypeTO> s);
    List<ModelStructureTypeTO> toTO(List<ModelStructureTypeDTO> s);
}
