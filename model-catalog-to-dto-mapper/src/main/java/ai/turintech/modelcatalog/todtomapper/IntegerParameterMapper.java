package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.to.IntegerParameterTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity {@link IntegerParameter} and its DTO {@link IntegerParameterDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "IntegerParameterMapperTOImpl")
public interface IntegerParameterMapper
        extends MapperInterface<IntegerParameterTO, IntegerParameterDTO> {

    List<IntegerParameterDTO> toDto(List<IntegerParameterTO> s);

    List<IntegerParameterTO> toTO(List<IntegerParameterDTO> s);
}
