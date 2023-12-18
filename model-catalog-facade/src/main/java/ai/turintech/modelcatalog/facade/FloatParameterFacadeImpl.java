package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link FloatParameter}. */
@Component
@Transactional
public class FloatParameterFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<FloatParameterDTO, UUID>
    implements FloatParameterFacade {}
