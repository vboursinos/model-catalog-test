package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link CategoricalParameter}. */
@Service
public class CategoricalParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        CategoricalParameterDTO, CategoricalParameter>
    implements CategoricalParameterService {}
