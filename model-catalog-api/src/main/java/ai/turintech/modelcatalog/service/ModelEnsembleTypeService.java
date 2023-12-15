package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import java.util.UUID;

public interface ModelEnsembleTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<
        ModelEnsembleTypeDTO, ModelEnsembleType, UUID> {}
