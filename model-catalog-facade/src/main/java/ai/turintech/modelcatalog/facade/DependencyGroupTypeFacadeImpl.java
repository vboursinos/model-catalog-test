package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.facade.impl.reactive.ReactiveAbstractUUIDIdentityCrudFacadeImpl;
import ai.turintech.modelcatalog.dto.DependencyGroupTypeDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ai.turintech.modelcatalog.entity.DependencyGroupType}.
 */
@Component
@Transactional
public class DependencyGroupTypeFacadeImpl
    extends ReactiveAbstractUUIDIdentityCrudFacadeImpl<DependencyGroupTypeDTO>
    implements DependencyGroupTypeFacade {}
