package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Service Implementation for managing {@link FloatParameter}. */
@Service
@Transactional
public class FloatParameterServiceImpl
    extends ReactiveAbstractCrudServiceImpl<FloatParameterDTO, FloatParameter, UUID>
    implements FloatParameterService {}
