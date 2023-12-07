package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ParameterType}. */
@Component
@Transactional
public class ParameterTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<ParameterTypeDTO, ParameterType, UUID>
    implements ParameterTypeFacade {}
