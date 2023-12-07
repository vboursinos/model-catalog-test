package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import java.util.UUID;

public interface BooleanParameterFacade
    extends ReactiveAbstractCrudFacade<BooleanParameterDTO, UUID> {}
