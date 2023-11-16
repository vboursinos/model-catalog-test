package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.to.FloatParameterTO;
import org.mapstruct.*;

import java.util.List;

/** Mapper for the entity {@link FloatParameter} and its DTO {@link FloatParameterDTO}. */
@Mapper(componentModel = "spring", implementationName = "FloatParameterMapperTOImpl")
public interface FloatParameterMapper extends MapperInterface<FloatParameterTO, FloatParameterDTO> {

    List<FloatParameterDTO> toDto(List<FloatParameterTO> s);

    List<FloatParameterTO> toTO(List<FloatParameterDTO> s);
}
