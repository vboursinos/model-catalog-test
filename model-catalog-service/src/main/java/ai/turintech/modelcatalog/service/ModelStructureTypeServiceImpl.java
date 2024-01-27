package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link ModelStructureType}. */
@Service
public class ModelStructureTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ModelStructureTypeDTO, ModelStructureType>
    implements ModelStructureTypeService {}
