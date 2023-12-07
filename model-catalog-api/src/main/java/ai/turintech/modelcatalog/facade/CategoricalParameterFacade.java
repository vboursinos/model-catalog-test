package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import java.util.UUID;

public interface CategoricalParameterFacade
    extends ReactiveAbstractCrudFacade<CategoricalParameterDTO, UUID> {}
