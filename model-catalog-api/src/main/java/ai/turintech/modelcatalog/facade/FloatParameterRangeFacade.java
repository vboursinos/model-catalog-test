package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import java.util.UUID;

public interface FloatParameterRangeFacade
    extends ReactiveUUIDIdentityCrudFacade<FloatParameterRangeDTO, UUID> {}
