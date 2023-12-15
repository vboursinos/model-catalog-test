package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import java.util.UUID;

public interface ParameterTypeDefinitionService
    extends ReactiveAbstractUUIDIdentityCrudService<
        ParameterTypeDefinitionDTO, ParameterTypeDefinition, UUID> {}
