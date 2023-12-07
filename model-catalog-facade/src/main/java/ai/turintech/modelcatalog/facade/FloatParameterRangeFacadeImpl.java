package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link FloatParameterRange} */
@Component
@Transactional
public class FloatParameterRangeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<FloatParameterRangeDTO, FloatParameterRange, UUID>
    implements FloatParameterRangeFacade {}
