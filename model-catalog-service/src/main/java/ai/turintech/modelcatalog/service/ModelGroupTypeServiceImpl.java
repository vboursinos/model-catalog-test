package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link ModelGroupType}. */
@Service
public class ModelGroupTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ModelGroupTypeDTO, ModelGroupType>
    implements ModelGroupTypeService {}
