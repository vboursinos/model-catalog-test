package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link FloatParameter}. */
@Service
public class FloatParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<FloatParameterDTO, FloatParameter>
    implements FloatParameterService {}
