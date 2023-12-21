package ai.turintech.modelcatalog.service;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudService;
import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.UUID;

public interface MetricService extends ReactiveUUIDIdentityCrudService<MetricDTO, UUID> {}
