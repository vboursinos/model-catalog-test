package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link CategoricalParameter}. */
@Service
@Transactional
public class CategoricalParameterServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        CategoricalParameterDTO, CategoricalParameter, UUID>
    implements CategoricalParameterService {}
