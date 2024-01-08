package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelType}. */
@Service
@Transactional
public class ModelTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ModelTypeDTO, ModelType>
    implements ModelTypeService {}
