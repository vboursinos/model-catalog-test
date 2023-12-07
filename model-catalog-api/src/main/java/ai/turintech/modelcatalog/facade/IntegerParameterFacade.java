package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import java.util.UUID;

public interface IntegerParameterFacade
    extends ReactiveAbstractCrudFacade<IntegerParameterDTO, UUID> {}
