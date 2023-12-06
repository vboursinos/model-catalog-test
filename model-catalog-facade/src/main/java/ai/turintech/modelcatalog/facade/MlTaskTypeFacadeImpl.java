package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link MlTaskType}. */
@Service
@Transactional
public class MlTaskTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<MlTaskTypeDTO, MlTaskType, UUID>
    implements MlTaskTypeFacade {}
