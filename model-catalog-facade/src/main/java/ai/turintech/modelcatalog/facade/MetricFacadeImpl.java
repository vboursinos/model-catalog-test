package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link Metric}. */
@Component
public class MetricFacadeImpl extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<MetricDTO>
    implements MetricFacade {}
