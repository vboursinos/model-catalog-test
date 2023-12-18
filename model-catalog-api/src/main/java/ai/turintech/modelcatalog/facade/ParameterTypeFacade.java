package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import java.util.UUID;

public interface ParameterTypeFacade
    extends ReactiveUUIDIdentityCrudFacade<ParameterTypeDTO, UUID> {}
