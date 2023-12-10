package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ParameterTypeDefinition}. */
@RestController
@RequestMapping("/api/parameter-type-definitions")
public class ParameterTypeDefinitionResource
    extends ReactiveAbstractCrudRestImpl<
        ParameterTypeDefinitionTO, ParameterTypeDefinitionDTO, UUID> {

  private static final String ENTITY_NAME = "modelCatalogParameterTypeDefinition";

  private static String APPLICATION_NAME = "model-catalog";

  public ParameterTypeDefinitionResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
