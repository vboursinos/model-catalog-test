package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link CategoricalParameterValue}. */
@Component
public class CategoricalParameterValueFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<CategoricalParameterValueDTO>
    implements CategoricalParameterValueFacade {}
