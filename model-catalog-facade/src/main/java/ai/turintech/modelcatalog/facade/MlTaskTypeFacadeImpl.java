package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link MlTaskType}. */
@Component
@Transactional
public class MlTaskTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<MlTaskTypeDTO, UUID>
    implements MlTaskTypeFacade {}
