package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import java.util.UUID;

public interface FloatParameterService
    extends ReactiveAbstractUUIDIdentityCrudService<FloatParameterDTO, FloatParameter, UUID> {}
