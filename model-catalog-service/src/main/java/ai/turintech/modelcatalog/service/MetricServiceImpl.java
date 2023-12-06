package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Service Implementation for managing {@link Metric}. */
@Service
@Transactional
public class MetricServiceImpl extends ReactiveAbstractCrudServiceImpl<MetricDTO, Metric, UUID>
    implements MetricService {}
