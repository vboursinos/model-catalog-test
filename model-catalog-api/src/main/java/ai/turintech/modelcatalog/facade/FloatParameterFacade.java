package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import java.util.UUID;

public interface FloatParameterFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<FloatParameterDTO, UUID> {}
