package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.to.FloatParameterRangeTO;
import java.util.List;
import org.mapstruct.Mapper;

/** Mapper for the entity {@link FloatParameterRange} and its DTO {@link FloatParameterRangeDTO}. */
@Mapper(componentModel = "spring", implementationName = "FloatParameterRangeMapperTOImpl")
public interface FloatParameterRangeMapper
    extends MapperInterface<FloatParameterRangeTO, FloatParameterRangeDTO> {

  List<FloatParameterRangeDTO> toDto(List<FloatParameterRangeTO> s);

  List<FloatParameterRangeTO> toTO(List<FloatParameterRangeDTO> s);
}
