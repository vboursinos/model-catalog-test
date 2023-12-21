package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import java.util.UUID;

public interface IntegerParameterValueService
    extends ReactiveUUIDIdentityCrudService<IntegerParameterValueDTO, UUID> {}
