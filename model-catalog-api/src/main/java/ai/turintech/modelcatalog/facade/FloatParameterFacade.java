package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import java.util.UUID;

public interface FloatParameterFacade
    extends ReactiveUUIDIdentityCrudFacade<FloatParameterDTO, UUID> {}
