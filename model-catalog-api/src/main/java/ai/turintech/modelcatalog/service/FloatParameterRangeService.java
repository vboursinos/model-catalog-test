package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import java.util.UUID;

public interface FloatParameterRangeService
    extends ReactiveUUIDIdentityCrudService<FloatParameterRangeDTO, UUID> {}
