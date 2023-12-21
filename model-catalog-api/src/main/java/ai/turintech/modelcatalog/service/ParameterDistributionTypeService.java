package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.UUID;

public interface ParameterDistributionTypeService
    extends ReactiveUUIDIdentityCrudService<ParameterDistributionTypeDTO, UUID> {}
