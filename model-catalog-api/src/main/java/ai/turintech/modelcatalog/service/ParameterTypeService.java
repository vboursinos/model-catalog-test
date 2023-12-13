package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import java.util.UUID;

public interface ParameterTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<ParameterTypeDTO, ParameterType, UUID> {}
