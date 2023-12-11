package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.UUID;

public interface MetricFacade extends ReactiveAbstractUUIDIdentityCrudFacade<MetricDTO, UUID> {}
