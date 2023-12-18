package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import java.util.UUID;

public interface ModelTypeFacade extends ReactiveUUIDIdentityCrudFacade<ModelTypeDTO, UUID> {}
