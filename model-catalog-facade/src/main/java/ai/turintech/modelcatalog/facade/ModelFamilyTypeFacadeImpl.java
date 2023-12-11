package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelFamilyType}. */
@Component
@Transactional
public class ModelFamilyTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelFamilyTypeDTO, ModelFamilyType, UUID>
    implements ModelFamilyTypeFacade {}
