package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import java.util.UUID;

public interface ModelFamilyTypeFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<ModelFamilyTypeDTO, UUID> {}
