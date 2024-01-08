package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link CategoricalParameterValue}. */
@Service
@Transactional
public class CategoricalParameterValueServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        CategoricalParameterValueDTO, CategoricalParameterValue>
    implements CategoricalParameterValueService {}
