package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link IntegerParameter}. */
@Component
@Transactional
public class IntegerParameterFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<IntegerParameterDTO, IntegerParameter, UUID>
    implements IntegerParameterFacade {}
