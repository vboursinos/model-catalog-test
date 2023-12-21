package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import java.util.UUID;

public interface FloatParameterService
    extends ReactiveUUIDIdentityCrudService<FloatParameterDTO, UUID> {}
