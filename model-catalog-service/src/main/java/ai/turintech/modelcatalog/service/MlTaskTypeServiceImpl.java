package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link MlTaskType}. */
@Service
@Transactional
public class MlTaskTypeServiceImpl
    extends ReactiveAbstractCrudServiceImpl<MlTaskTypeDTO, MlTaskType>
    implements MlTaskTypeService {}
