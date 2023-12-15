package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelStructureType}. */
@Component
@Transactional
public class ModelStructureTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<
        ModelStructureTypeDTO, ModelStructureType, UUID>
    implements ModelStructureTypeFacade {}
