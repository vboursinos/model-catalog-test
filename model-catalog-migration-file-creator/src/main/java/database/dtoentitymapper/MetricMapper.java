package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.MetricDTO;
import database.entity.Metric;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MetricMapper extends MapperInterface<MetricDTO, Metric> {}
