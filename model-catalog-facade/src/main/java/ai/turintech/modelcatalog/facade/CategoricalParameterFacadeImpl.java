package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link CategoricalParameter} */
@Component
public class CategoricalParameterFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<CategoricalParameterDTO>
    implements CategoricalParameterFacade {}
