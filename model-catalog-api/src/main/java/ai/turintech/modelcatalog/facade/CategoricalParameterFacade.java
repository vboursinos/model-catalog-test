package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import java.util.UUID;

public interface CategoricalParameterFacade
    extends ReactiveUUIDIdentityCrudFacade<CategoricalParameterDTO, UUID> {}
