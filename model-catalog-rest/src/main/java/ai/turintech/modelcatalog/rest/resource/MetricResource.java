package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import ai.turintech.modelcatalog.to.MetricTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link Metric}. */
@RestController
@RequestMapping("/api/metrics")
public class MetricResource extends ReactiveAbstractUUIDIdentityCrudRestImpl<MetricTO, MetricDTO> {

  private final Logger log = LoggerFactory.getLogger(MetricResource.class);

  private static final String ENTITY_NAME = "modelCatalogMetric";
  private static String APPLICATION_NAME = "model-catalog";

  public MetricResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
