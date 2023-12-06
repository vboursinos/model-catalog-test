package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link FloatParameterRange}. */
@Service
@Transactional
public class FloatParameterRangeServiceImpl
    extends ReactiveAbstractCrudServiceImpl<FloatParameterRangeDTO, FloatParameterRange>
    implements FloatParameterRangeService {}
