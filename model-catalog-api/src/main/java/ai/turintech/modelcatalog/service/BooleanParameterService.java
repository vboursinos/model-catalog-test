package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import java.util.UUID;

public interface BooleanParameterService
    extends ReactiveAbstractUUIDIdentityCrudService<BooleanParameterDTO, BooleanParameter, UUID> {}
