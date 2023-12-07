package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import java.util.UUID;

public interface ModelGroupTypeFacade extends ReactiveAbstractCrudFacade<ModelGroupTypeDTO, UUID> {}
