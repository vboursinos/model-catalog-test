package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.service.impl.reactive.ReactiveAbstractUUIDIdentityCrudServiceImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link IntegerParameterValue}. */
@Service
@Transactional
public class IntegerParameterValueServiceImpl
    extends ReactiveAbstractUUIDIdentityCrudServiceImpl<
        IntegerParameterValueDTO, IntegerParameterValue, UUID>
    implements IntegerParameterValueService {}
