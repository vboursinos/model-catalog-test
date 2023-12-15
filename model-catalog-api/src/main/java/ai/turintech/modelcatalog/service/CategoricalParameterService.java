package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import java.util.UUID;

public interface CategoricalParameterService
    extends ReactiveAbstractUUIDIdentityCrudService<
        CategoricalParameterDTO, CategoricalParameter, UUID> {}
