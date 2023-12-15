package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import java.util.UUID;

public interface FloatParameterRangeService
    extends ReactiveAbstractUUIDIdentityCrudService<
        FloatParameterRangeDTO, FloatParameterRange, UUID> {}
