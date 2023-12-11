package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import java.util.UUID;

public interface ModelGroupTypeFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<ModelGroupTypeDTO, UUID> {}
