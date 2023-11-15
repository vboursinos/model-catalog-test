package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.to.MetricTO;
import org.mapstruct.*;

/** Mapper for the entity {@link Metric} and its DTO {@link MetricDTO}. */
@Mapper(componentModel = "spring", implementationName = "MetricMapperTOImpl")
public interface MetricMapper extends EntityMapper<MetricTO, MetricDTO> {}
