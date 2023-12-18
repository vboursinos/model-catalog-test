package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import java.util.UUID;

public interface BooleanParameterFacade
    extends ReactiveUUIDIdentityCrudFacade<BooleanParameterDTO, UUID> {}
