package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelType}. */
@Service
@Transactional
public class ModelTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<ModelTypeDTO, ModelType, UUID>
    implements ModelTypeFacade {}
