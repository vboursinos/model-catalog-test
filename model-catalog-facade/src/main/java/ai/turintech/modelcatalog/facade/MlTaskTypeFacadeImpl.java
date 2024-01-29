package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import org.springframework.stereotype.Component;

/** Service Implementation for managing {@link MlTaskType}. */
@Component
public class MlTaskTypeFacadeImpl extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<MlTaskTypeDTO>
    implements MlTaskTypeFacade {}
