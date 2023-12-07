package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link FloatParameter}. */
@Service
@Transactional
public class FloatParameterFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<FloatParameterDTO, FloatParameter, UUID>
    implements FloatParameterFacade {}
