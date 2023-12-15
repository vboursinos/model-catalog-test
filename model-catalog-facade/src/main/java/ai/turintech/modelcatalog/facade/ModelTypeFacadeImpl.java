package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ModelType}. */
@Component
@Transactional
public class ModelTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<ModelTypeDTO, ModelType, UUID>
    implements ModelTypeFacade {}
