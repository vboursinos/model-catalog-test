package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import java.util.UUID;

public interface IntegerParameterValueFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<IntegerParameterValueDTO, UUID> {}
