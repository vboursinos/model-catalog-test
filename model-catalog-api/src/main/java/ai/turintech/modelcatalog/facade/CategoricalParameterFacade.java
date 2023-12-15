package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import java.util.UUID;

public interface CategoricalParameterFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<CategoricalParameterDTO, UUID> {}
