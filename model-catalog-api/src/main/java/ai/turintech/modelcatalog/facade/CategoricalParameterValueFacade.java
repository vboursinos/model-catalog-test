package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import java.util.UUID;

public interface CategoricalParameterValueFacade
    extends ReactiveAbstractCrudFacade<CategoricalParameterValueDTO, UUID> {}
