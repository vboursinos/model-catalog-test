package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import java.util.UUID;

public interface ModelEnsembleTypeService
    extends ReactiveUUIDIdentityCrudService<ModelEnsembleTypeDTO, UUID> {}
