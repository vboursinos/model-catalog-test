package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ParameterDistributionType}. */
@Service
@Transactional
public class ParameterDistributionTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        ParameterDistributionTypeDTO, ParameterDistributionType, UUID>
    implements ParameterDistributionTypeService {}
