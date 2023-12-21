package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import java.util.UUID;

public interface BooleanParameterService
    extends ReactiveUUIDIdentityCrudService<BooleanParameterDTO, UUID> {}
