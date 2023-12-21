package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import java.util.UUID;

public interface CategoricalParameterValueService
    extends ReactiveUUIDIdentityCrudService<CategoricalParameterValueDTO, UUID> {}
