package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import java.util.UUID;

public interface MlTaskTypeService extends ReactiveUUIDIdentityCrudService<MlTaskTypeDTO, UUID> {}
