package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link ParameterType}. */
@Service
@Transactional
public class ParameterTypeServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<ParameterTypeDTO, ParameterType, UUID>
    implements ParameterTypeService {}
