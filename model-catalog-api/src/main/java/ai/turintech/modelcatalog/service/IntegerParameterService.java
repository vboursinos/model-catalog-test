package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import java.util.UUID;

public interface IntegerParameterService
    extends ReactiveAbstractUUIDIdentityCrudService<IntegerParameterDTO, IntegerParameter, UUID> {}
