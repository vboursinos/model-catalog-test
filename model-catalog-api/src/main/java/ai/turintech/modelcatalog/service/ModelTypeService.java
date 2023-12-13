package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import java.util.UUID;

public interface ModelTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<ModelTypeDTO, ModelType, UUID> {}
