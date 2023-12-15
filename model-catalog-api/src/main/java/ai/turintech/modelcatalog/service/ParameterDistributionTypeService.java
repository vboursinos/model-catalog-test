package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import java.util.UUID;

public interface ParameterDistributionTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<
        ParameterDistributionTypeDTO, ParameterDistributionType, UUID> {}
