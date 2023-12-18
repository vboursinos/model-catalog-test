package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import java.util.UUID;

public interface ModelFamilyTypeFacade
    extends ReactiveUUIDIdentityCrudFacade<ModelFamilyTypeDTO, UUID> {}
