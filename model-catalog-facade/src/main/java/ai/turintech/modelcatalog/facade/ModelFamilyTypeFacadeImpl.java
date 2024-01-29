package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link ModelFamilyType}. */
@Component
public class ModelFamilyTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelFamilyTypeDTO>
    implements ModelFamilyTypeFacade {}
