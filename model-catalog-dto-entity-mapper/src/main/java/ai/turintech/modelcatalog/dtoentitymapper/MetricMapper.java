package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Metric} and its DTO {@link MetricDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetricMapper extends EntityMapper<MetricDTO, Metric> {}
