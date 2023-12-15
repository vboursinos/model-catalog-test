package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import java.util.UUID;

public interface IntegerParameterValueService
    extends ReactiveAbstractUUIDIdentityCrudService<
        IntegerParameterValueDTO, IntegerParameterValue, UUID> {}
