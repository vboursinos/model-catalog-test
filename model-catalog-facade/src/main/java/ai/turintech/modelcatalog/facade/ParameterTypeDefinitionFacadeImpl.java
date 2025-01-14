package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link ParameterTypeDefinition}. */
@Component
public class ParameterTypeDefinitionFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ParameterTypeDefinitionDTO>
    implements ParameterTypeDefinitionFacade {}
