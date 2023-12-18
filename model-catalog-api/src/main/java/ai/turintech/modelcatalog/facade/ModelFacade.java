package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.UUID;

public interface ModelFacade extends ReactiveUUIDIdentityCrudFacade<ModelDTO, UUID> {}
