package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import java.util.UUID;

public interface MlTaskTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<MlTaskTypeDTO, MlTaskType, UUID> {}
