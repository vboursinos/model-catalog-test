package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ParameterDistributionType}. */
@Component
@Transactional
public class ParameterDistributionTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ParameterDistributionTypeDTO>
    implements ParameterDistributionTypeFacade {}
