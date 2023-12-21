package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import java.util.UUID;

public interface CategoricalParameterService
    extends ReactiveUUIDIdentityCrudService<CategoricalParameterDTO, UUID> {}
