package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.UUID;

public interface ModelFacade extends ReactiveAbstractUUIDIdentityCrudFacade<ModelDTO, UUID> {}
