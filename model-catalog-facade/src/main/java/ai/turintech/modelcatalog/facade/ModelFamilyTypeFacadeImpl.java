package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelFamilyType}. */
@Service
@Transactional
public class ModelFamilyTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<ModelFamilyTypeDTO, ModelFamilyType, UUID>
    implements ModelFamilyTypeFacade {}
