package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link IntegerParameter}. */
@Service
@Transactional
public class IntegerParameterServiceImpl
    extends ReactiveAbstractCrudServiceImpl<IntegerParameterDTO, IntegerParameter>
    implements IntegerParameterService {}
