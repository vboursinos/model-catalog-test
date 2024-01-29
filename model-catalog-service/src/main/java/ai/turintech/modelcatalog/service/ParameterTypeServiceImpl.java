package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link ParameterType}. */
@Service
public class ParameterTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ParameterTypeDTO, ParameterType>
    implements ParameterTypeService {}
