package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link IntegerParameter}. */
@Component
public class IntegerParameterFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<IntegerParameterDTO>
    implements IntegerParameterFacade {}
