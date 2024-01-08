package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link Metric}. */
@Service
@Transactional
public class MetricServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<MetricDTO, Metric>
    implements MetricService {}
