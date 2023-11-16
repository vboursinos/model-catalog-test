package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.to.ModelGroupTypeTO;
import org.mapstruct.*;

import java.util.List;

/** Mapper for the entity {@link ModelGroupType} and its DTO {@link ModelGroupTypeDTO}. */
@Mapper(componentModel = "spring", implementationName = "ModelGroupTypeMapperTOImpl")
public interface ModelGroupTypeMapper extends MapperInterface<ModelGroupTypeTO, ModelGroupTypeDTO> {
    List<ModelGroupTypeDTO> toDto(List<ModelGroupTypeTO> s);

    List<ModelGroupTypeTO> toTO(List<ModelGroupTypeDTO> s);
}
