package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractCrudServiceImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link CategoricalParameter}. */
@Service
@Transactional
public class CategoricalParameterServiceImpl
    extends ReactiveAbstractCrudServiceImpl<CategoricalParameterDTO, CategoricalParameter, UUID>
    implements CategoricalParameterService {}
