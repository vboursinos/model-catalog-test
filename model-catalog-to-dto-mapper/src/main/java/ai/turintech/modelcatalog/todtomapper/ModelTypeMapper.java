package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.ModelTO;
import ai.turintech.modelcatalog.to.ModelTypeTO;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link ModelType} and its DTO {@link ModelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelTypeMapper extends EntityMapper<ModelTypeTO, ModelTypeDTO> {
    ModelTypeDTO toDto(ModelTypeTO s);

    ModelTypeTO toTO(ModelTypeDTO s);

    List<ModelTypeTO> toTO(List<ModelTypeDTO> s);
}
