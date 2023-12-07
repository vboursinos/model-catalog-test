package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import java.util.UUID;

public interface ModelStructureTypeFacade
    extends ReactiveAbstractCrudFacade<ModelStructureTypeDTO, UUID> {}
