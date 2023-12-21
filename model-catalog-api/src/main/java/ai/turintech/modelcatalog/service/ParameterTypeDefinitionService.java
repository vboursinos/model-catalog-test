package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.UUID;

public interface ParameterTypeDefinitionService
    extends ReactiveUUIDIdentityCrudService<ParameterTypeDefinitionDTO, UUID> {}
