package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import java.util.UUID;

public interface MlTaskTypeFacade extends ReactiveUUIDIdentityCrudFacade<MlTaskTypeDTO, UUID> {}
