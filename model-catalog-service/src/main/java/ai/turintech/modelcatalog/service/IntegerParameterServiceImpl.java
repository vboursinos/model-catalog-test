package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link IntegerParameter}. */
@Service
@Transactional
public class IntegerParameterServiceImpl
    extends ReactiveAbstractCrudServiceImpl<IntegerParameterDTO, IntegerParameter, UUID>
    implements IntegerParameterService {}
