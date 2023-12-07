package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link MlTaskType}. */
@Service
@Transactional
public class MlTaskTypeServiceImpl
    extends ReactiveAbstractCrudServiceImpl<MlTaskTypeDTO, MlTaskType, UUID>
    implements MlTaskTypeService {}
