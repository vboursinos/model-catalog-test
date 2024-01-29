package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link FloatParameterRange} */
@Component
public class FloatParameterRangeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<FloatParameterRangeDTO>
    implements FloatParameterRangeFacade {}
