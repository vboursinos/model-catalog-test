package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.to.BooleanParameterTO;
import org.mapstruct.*;

import java.util.List;

/** Mapper for the entity {@link BooleanParameter} and its DTO {@link BooleanParameterDTO}. */
@Mapper(componentModel = "spring", implementationName = "BooleanParameterMapperTOImpl")
public interface BooleanParameterMapper
    extends MapperInterface<BooleanParameterTO, BooleanParameterDTO> {

    List<BooleanParameterDTO> toDTO(List<BooleanParameterTO> toList);

    List<BooleanParameterTO> toTO(List<BooleanParameterDTO> dtoList);
}
