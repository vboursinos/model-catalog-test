package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelEnsembleType}. */
@Service
@Transactional
public class ModelEnsembleTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ModelEnsembleTypeDTO, ModelEnsembleType>
    implements ModelEnsembleTypeService {}
