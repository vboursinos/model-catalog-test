package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link CategoricalParameterValue}. */
@Component
@Transactional
public class CategoricalParameterValueFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<
        CategoricalParameterValueDTO, CategoricalParameterValue, UUID>
    implements CategoricalParameterValueFacade {}
