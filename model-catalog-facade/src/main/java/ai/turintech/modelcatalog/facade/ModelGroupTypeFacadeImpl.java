package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelGroupType}. */
@Service
@Transactional
public class ModelGroupTypeFacadeImpl
    extends ReactiveAbstractCrudFacadeImpl<ModelGroupTypeDTO, ModelGroupType, UUID>
    implements ModelGroupTypeFacade {}
