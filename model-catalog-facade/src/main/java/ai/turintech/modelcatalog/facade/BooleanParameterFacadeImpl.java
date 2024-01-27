package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link BooleanParameter}. */
@Component
public class BooleanParameterFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<BooleanParameterDTO>
    implements BooleanParameterFacade {}
