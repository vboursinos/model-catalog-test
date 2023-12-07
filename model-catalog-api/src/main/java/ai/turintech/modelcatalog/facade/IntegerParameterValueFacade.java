package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import java.util.UUID;

public interface IntegerParameterValueFacade
    extends ReactiveAbstractCrudFacade<IntegerParameterValueDTO, UUID> {}
