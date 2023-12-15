package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link CategoricalParameterValue}. */
@RestController
@RequestMapping("/api/categorical-parameter-values")
public class CategoricalParameterValueResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<
        CategoricalParameterValueTO, CategoricalParameterValueDTO, UUID> {

  private static final String ENTITY_NAME = "modelCatalogCategoricalParameterValue";

  private static String APPLICATION_NAME = "model-catalog";

  public CategoricalParameterValueResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
