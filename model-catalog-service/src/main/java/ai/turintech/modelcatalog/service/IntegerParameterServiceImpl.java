package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link IntegerParameter}. */
@Service
public class IntegerParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<IntegerParameterDTO, IntegerParameter>
    implements IntegerParameterService {}
