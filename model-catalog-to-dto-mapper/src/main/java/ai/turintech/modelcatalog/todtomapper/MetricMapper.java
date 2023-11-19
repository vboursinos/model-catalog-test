package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.to.MetricTO;
import java.util.List;
import org.mapstruct.*;

/** Mapper for the entity {@link Metric} and its DTO {@link MetricDTO}. */
@Mapper(componentModel = "spring", implementationName = "MetricMapperTOImpl")
public interface MetricMapper extends MapperInterface<MetricTO, MetricDTO> {

  List<MetricDTO> toDTO(List<MetricTO> s);

  List<MetricTO> toTO(List<MetricDTO> s);
}
