package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import java.util.UUID;

public interface ParameterTypeService
    extends ReactiveUUIDIdentityCrudService<ParameterTypeDTO, UUID> {}
