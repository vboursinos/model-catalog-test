package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.UUID;

public interface ParameterDistributionTypeFacade
    extends ReactiveUUIDIdentityCrudFacade<ParameterDistributionTypeDTO, UUID> {}
