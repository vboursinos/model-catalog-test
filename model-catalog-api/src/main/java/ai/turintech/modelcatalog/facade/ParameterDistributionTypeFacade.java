package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.UUID;

public interface ParameterDistributionTypeFacade
    extends ReactiveAbstractUUIDIdentityCrudFacade<ParameterDistributionTypeDTO, UUID> {}
