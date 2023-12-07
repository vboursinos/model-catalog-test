package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelEnsembleType}. */
@Service
@Transactional
public class ModelEnsembleTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<ModelEnsembleTypeDTO, ModelEnsembleType, UUID>
    implements ModelEnsembleTypeFacade {}
