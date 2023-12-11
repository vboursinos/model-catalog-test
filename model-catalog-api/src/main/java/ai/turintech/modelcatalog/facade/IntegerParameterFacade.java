package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import java.util.UUID;

public interface IntegerParameterFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<IntegerParameterDTO, UUID> {}
