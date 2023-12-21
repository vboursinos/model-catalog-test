package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import java.util.UUID;

public interface ModelFamilyTypeService
    extends ReactiveUUIDIdentityCrudService<ModelFamilyTypeDTO, UUID> {}
