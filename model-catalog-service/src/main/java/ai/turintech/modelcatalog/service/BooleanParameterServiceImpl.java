package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link BooleanParameter}. */
@Service
public class BooleanParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<BooleanParameterDTO, BooleanParameter>
    implements BooleanParameterService {}
