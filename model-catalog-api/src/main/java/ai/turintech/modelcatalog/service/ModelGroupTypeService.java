package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import java.util.UUID;

public interface ModelGroupTypeService
    extends ReactiveAbstractUUIDIdentityCrudService<ModelGroupTypeDTO, ModelGroupType, UUID> {}
