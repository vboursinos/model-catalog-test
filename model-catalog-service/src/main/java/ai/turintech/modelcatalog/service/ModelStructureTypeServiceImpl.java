package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelStructureType}. */
@Service
@Transactional
public class ModelStructureTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ModelStructureTypeDTO, ModelStructureType>
    implements ModelStructureTypeService {}