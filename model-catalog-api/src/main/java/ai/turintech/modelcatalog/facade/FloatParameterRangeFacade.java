package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import java.util.UUID;

public interface FloatParameterRangeFacade
    extends ReactiveAbstractCrudFacade<FloatParameterRangeDTO, UUID> {}
