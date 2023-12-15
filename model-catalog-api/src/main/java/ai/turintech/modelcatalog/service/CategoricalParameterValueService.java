package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import java.util.UUID;

public interface CategoricalParameterValueService
    extends ReactiveAbstractUUIDIdentityCrudService<
        CategoricalParameterValueDTO, CategoricalParameterValue, UUID> {}
