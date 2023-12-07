package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import java.util.UUID;

public interface ModelEnsembleTypeFacade
    extends ReactiveAbstractCrudFacade<ModelEnsembleTypeDTO, UUID> {}
