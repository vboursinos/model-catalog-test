package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.to.IntegerParameterValueTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity {@link IntegerParameterValue} and its DTO {@link IntegerParameterValueDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "IntegerParameterValueMapperTOImpl")
public interface IntegerParameterValueMapper
        extends MapperInterface<IntegerParameterValueTO, IntegerParameterValueDTO> {

    List<IntegerParameterValueDTO> toDto(List<IntegerParameterValueTO> s);

    List<IntegerParameterValueTO> toTO(List<IntegerParameterValueDTO> s);
}
