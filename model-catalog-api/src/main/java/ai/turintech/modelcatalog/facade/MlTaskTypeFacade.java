package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import java.util.UUID;

public interface MlTaskTypeFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<MlTaskTypeDTO, UUID> {}
