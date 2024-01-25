package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import ai.turintech.modelcatalog.to.FloatParameterRangeTO;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link FloatParameterRange}. */
@RestController
@RequestMapping("/api/float-parameter-ranges")
public class FloatParameterRangeResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<
        FloatParameterRangeTO, FloatParameterRangeDTO> {
  private static final String ENTITY_NAME = "modelCatalogFloatParameterRange";

  private static String APPLICATION_NAME = "model-catalog";

  public FloatParameterRangeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
