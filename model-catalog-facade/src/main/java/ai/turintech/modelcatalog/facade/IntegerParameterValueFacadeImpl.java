package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link IntegerParameterValue}. */
@Component
public class IntegerParameterValueFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<IntegerParameterValueDTO>
    implements IntegerParameterValueFacade {}
