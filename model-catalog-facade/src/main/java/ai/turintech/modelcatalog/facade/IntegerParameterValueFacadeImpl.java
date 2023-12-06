package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link IntegerParameterValue}. */
@Service
@Transactional
public class IntegerParameterValueFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<IntegerParameterValueDTO, IntegerParameterValue, UUID>
    implements IntegerParameterValueFacade {}
