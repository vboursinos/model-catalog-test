package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import java.util.UUID;

public interface MetricService
    extends ReactiveAbstractUUIDIdentityCrudService<MetricDTO, Metric, UUID> {}
