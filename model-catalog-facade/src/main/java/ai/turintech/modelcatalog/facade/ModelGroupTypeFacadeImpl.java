package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelGroupType}. */
@Component
@Transactional
public class ModelGroupTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelGroupTypeDTO>
    implements ModelGroupTypeFacade {}
