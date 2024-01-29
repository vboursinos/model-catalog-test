package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link ModelFamilyType}. */
@Service
public class ModelFamilyTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ModelFamilyTypeDTO, ModelFamilyType>
    implements ModelFamilyTypeService {}
