package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import java.util.UUID;

public interface ModelGroupTypeFacade
    extends ReactiveUUIDIdentityCrudFacade<ModelGroupTypeDTO, UUID> {}
