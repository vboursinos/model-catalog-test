package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.DependencyGroupTypeDTO;
import ai.turintech.modelcatalog.entity.DependencyGroupType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link DependencyGroupType}. */
@Service
@Transactional
public class DependencyGroupTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<DependencyGroupTypeDTO, DependencyGroupType>
    implements DependencyGroupTypeService {}
