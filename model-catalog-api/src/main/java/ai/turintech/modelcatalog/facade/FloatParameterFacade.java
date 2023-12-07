package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import java.util.UUID;

public interface FloatParameterFacade extends ReactiveAbstractCrudFacade<FloatParameterDTO, UUID> {}
