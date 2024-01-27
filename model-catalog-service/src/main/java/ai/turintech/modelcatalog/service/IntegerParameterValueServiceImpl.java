package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link IntegerParameterValue}. */
@Service
public class IntegerParameterValueServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        IntegerParameterValueDTO, IntegerParameterValue>
    implements IntegerParameterValueService {}
