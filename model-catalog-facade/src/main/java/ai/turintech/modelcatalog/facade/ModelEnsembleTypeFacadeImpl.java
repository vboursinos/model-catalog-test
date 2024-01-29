package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link ModelEnsembleType}. */
@Component
public class ModelEnsembleTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelEnsembleTypeDTO>
    implements ModelEnsembleTypeFacade {}
