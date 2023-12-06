package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link Metric}. */
@Service
@Transactional
public class MetricFacadeImpl extends ReactiveAbstractCrudFacadeImpl<MetricDTO, Metric, UUID>
    implements MetricFacade {}
