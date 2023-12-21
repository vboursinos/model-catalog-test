package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import java.util.UUID;

public interface ModelStructureTypeService
    extends ReactiveUUIDIdentityCrudService<ModelStructureTypeDTO, UUID> {}
