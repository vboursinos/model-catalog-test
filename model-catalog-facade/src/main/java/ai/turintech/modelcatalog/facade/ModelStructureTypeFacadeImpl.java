package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link ModelStructureType}. */
@Component
public class ModelStructureTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelStructureTypeDTO>
    implements ModelStructureTypeFacade {}
