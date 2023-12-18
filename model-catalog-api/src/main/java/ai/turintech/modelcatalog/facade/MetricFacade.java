package ai.turintech.modelcatalog.facade;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.UUID;

public interface MetricFacade extends ReactiveUUIDIdentityCrudFacade<MetricDTO, UUID> {}
