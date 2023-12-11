package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import java.util.UUID;

public interface ModelStructureTypeFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<ModelStructureTypeDTO, UUID> {}
