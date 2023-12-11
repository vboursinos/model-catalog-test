package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import java.util.UUID;

public interface CategoricalParameterValueFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<CategoricalParameterValueDTO, UUID> {}
