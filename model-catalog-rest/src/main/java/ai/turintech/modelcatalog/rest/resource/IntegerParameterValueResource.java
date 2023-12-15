package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import ai.turintech.modelcatalog.to.IntegerParameterValueTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link IntegerParameterValue}. */
@RestController
@RequestMapping("/api/integer-parameter-values")
public class IntegerParameterValueResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<
        IntegerParameterValueTO, IntegerParameterValueDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogIntegerParameterValue";

  private static String APPLICATION_NAME = "model-catalog";

  public IntegerParameterValueResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
