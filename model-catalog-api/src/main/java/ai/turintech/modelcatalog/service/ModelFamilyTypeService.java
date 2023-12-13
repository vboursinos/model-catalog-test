package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import java.util.UUID;

public interface ModelFamilyTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<ModelFamilyTypeDTO, ModelFamilyType, UUID> {}
