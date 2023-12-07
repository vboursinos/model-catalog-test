package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudFacade;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.UUID;

public interface ParameterDistributionTypeFacade
    extends ReactiveAbstractCrudFacade<ParameterDistributionTypeDTO, UUID> {}
