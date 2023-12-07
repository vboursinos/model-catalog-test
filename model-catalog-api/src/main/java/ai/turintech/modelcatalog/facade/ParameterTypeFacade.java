package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import java.util.UUID;

public interface ParameterTypeFacade extends ReactiveAbstractCrudFacade<ParameterTypeDTO, UUID> {}
