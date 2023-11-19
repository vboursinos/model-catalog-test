package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
import java.util.List;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameterValue} and its DTO {@link
 * CategoricalParameterValueDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "CategoricalParameterValueMapperTOImpl")
public interface CategoricalParameterValueMapper
    extends MapperInterface<CategoricalParameterValueTO, CategoricalParameterValueDTO> {

  List<CategoricalParameterValueDTO> toDto(List<CategoricalParameterValueTO> s);

  List<CategoricalParameterValueTO> toTO(List<CategoricalParameterValueDTO> s);
}
