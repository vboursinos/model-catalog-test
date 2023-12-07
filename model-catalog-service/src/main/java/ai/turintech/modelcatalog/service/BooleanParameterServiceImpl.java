package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link BooleanParameter}. */
@Service
@Transactional
public class BooleanParameterServiceImpl
    extends ReactiveAbstractCrudServiceImpl<BooleanParameterDTO, BooleanParameter, UUID>
    implements BooleanParameterService {}
