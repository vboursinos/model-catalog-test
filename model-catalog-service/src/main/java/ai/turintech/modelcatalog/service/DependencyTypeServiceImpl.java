package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.DependencyTypeDTO;
import ai.turintech.modelcatalog.entity.DependencyType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link DependencyType}. */
@Service
public class DependencyTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<DependencyTypeDTO, DependencyType>
    implements DependencyTypeService {}
