package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.UUID;

public interface ParameterTypeDefinitionFacade
    extends ReactiveAbstractCrudFacade<ParameterTypeDefinitionDTO, UUID> {}
