package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link MlTaskType}. */
@Service
public class MlTaskTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<MlTaskTypeDTO, MlTaskType>
    implements MlTaskTypeService {}
