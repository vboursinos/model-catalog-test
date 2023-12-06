package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Service Implementation for managing {@link ParameterType}. */
@Service
@Transactional
public class ParameterTypeServiceImpl
    extends ReactiveAbstractCrudServiceImpl<ParameterTypeDTO, ParameterType, UUID>
    implements ParameterTypeService {}
