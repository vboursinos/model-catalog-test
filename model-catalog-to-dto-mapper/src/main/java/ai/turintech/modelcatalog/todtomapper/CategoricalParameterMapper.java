package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.to.CategoricalParameterTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link CategoricalParameter} and its DTO {@link CategoricalParameterDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "CategoricalParameterMapperTOImpl")
public interface CategoricalParameterMapper
    extends MapperInterface<CategoricalParameterTO, CategoricalParameterDTO> {

    List<CategoricalParameterDTO> toDTOList(List<CategoricalParameterTO> toList);

    List<CategoricalParameterTO> toTOList(List<CategoricalParameterDTO> dtoList);
}
