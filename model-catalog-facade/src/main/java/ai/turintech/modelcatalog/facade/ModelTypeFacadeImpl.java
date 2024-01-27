package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link ModelType}. */
@Component
public class ModelTypeFacadeImpl extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelTypeDTO>
    implements ModelTypeFacade {}
