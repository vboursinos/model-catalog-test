package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ParameterDistributionType}. */
@Service
@Transactional
public class ParameterDistributionTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<
        ParameterDistributionTypeDTO, ParameterDistributionType, UUID>
    implements ParameterDistributionTypeFacade {}
