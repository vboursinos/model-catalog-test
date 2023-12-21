package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import java.util.UUID;

public interface IntegerParameterService
    extends ReactiveUUIDIdentityCrudService<IntegerParameterDTO, UUID> {}
