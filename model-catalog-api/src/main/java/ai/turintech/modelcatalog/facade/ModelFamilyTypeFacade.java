package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import java.util.UUID;

public interface ModelFamilyTypeFacade
    extends ReactiveAbstractCrudFacade<ModelFamilyTypeDTO, UUID> {}
