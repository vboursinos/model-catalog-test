package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import java.util.UUID;

public interface ModelTypeService extends ReactiveUUIDIdentityCrudService<ModelTypeDTO, UUID> {}
