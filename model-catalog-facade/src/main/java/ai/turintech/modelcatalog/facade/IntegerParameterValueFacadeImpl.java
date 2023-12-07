package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link IntegerParameterValue}. */
@Component
@Transactional
public class IntegerParameterValueFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<IntegerParameterValueDTO, IntegerParameterValue, UUID>
    implements IntegerParameterValueFacade {}
