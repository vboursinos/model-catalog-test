package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.DependencyTypeDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ai.turintech.modelcatalog.entity.DependencyType}. */
@Component
public class DependencyTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<DependencyTypeDTO>
    implements DependencyTypeFacade {}
