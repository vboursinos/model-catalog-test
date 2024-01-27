package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link FloatParameter}. */
@Component
public class FloatParameterFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<FloatParameterDTO>
    implements FloatParameterFacade {}
