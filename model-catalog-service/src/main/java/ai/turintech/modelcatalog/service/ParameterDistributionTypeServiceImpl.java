package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link ParameterDistributionType}. */
@Service
public class ParameterDistributionTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        ParameterDistributionTypeDTO, ParameterDistributionType>
    implements ParameterDistributionTypeService {}
