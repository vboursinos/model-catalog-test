package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Service Implementation for managing {@link ParameterDistributionType}. */
@Service
@Transactional
public class ParameterDistributionTypeServiceImpl
    extends ReactiveAbstractCrudServiceImpl<ParameterDistributionTypeDTO, ParameterDistributionType, UUID>
    implements ParameterDistributionTypeService {}
