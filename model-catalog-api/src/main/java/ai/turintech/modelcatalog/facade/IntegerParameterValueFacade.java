package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import java.util.UUID;

public interface IntegerParameterValueFacade
    extends ReactiveUUIDIdentityCrudFacade<IntegerParameterValueDTO, UUID> {}
