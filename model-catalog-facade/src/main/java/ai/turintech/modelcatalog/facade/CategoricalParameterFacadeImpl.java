package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link CategoricalParameter} */
@Component
@Transactional
public class CategoricalParameterFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<CategoricalParameterDTO, CategoricalParameter, UUID>
    implements CategoricalParameterFacade {}
