package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link FloatParameter}. */
@Service
@Transactional
public class FloatParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<FloatParameterDTO, FloatParameter, UUID>
    implements FloatParameterService {}
