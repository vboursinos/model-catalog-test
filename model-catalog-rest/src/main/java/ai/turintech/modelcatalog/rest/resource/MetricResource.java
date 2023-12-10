package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import ai.turintech.modelcatalog.to.MetricTO;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link Metric}. */
@RestController
@RequestMapping("/api/metrics")
public class MetricResource extends ReactiveAbstractCrudRestImpl<MetricTO, MetricDTO, UUID> {

  private final Logger log = LoggerFactory.getLogger(MetricResource.class);

  private static final String ENTITY_NAME = "modelCatalogMetric";
  private static String APPLICATION_NAME = "model-catalog";

  public MetricResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
