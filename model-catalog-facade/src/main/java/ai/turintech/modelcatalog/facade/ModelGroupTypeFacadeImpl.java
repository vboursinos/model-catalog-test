package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link ModelGroupType}. */
@Component
public class ModelGroupTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelGroupTypeDTO>
    implements ModelGroupTypeFacade {}
