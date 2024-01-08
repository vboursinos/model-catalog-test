package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ParameterType}. */
@Component
@Transactional
public class ParameterTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ParameterTypeDTO>
    implements ParameterTypeFacade {}
