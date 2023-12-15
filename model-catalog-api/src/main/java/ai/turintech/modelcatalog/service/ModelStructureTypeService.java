package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import java.util.UUID;

public interface ModelStructureTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<
        ModelStructureTypeDTO, ModelStructureType, UUID> {}
