package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import ai.turintech.modelcatalog.to.FloatParameterRangeTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link FloatParameterRange}. */
@RestController
@RequestMapping("/api/float-parameter-ranges")
public class FloatParameterRangeResource
    extends ReactiveAbstractCrudRestImpl<FloatParameterRangeTO, FloatParameterRangeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogFloatParameterRange";

  private static String APPLICATION_NAME = "model-catalog";

  public FloatParameterRangeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
