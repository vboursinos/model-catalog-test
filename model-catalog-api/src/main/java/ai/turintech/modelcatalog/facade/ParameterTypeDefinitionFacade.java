package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.UUID;

public interface ParameterTypeDefinitionFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<ParameterTypeDefinitionDTO, UUID> {}
