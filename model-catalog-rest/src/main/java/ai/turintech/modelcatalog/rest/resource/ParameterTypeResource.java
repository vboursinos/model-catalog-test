package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.to.ParameterTypeTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ParameterType}. */
@RestController
@RequestMapping("/api/parameter-types")
public class ParameterTypeResource
    extends ReactiveAbstractCrudRestImpl<ParameterTypeTO, ParameterTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogParameterType";

  private static String APPLICATION_NAME = "model-catalog";

  public ParameterTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
