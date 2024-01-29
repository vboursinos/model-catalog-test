package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import org.springframework.stereotype.Service;

/** Service Implementation for managing {@link CategoricalParameterValue}. */
@Service
public class CategoricalParameterValueServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        CategoricalParameterValueDTO, CategoricalParameterValue>
    implements CategoricalParameterValueService {}
