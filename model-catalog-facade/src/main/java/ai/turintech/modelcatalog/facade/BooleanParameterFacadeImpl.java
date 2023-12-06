package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link BooleanParameter}. */
@Service
@Transactional
public class BooleanParameterFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<BooleanParameterDTO, BooleanParameter, UUID>
    implements BooleanParameterFacade {}
