package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import ai.turintech.modelcatalog.to.ModelFamilyTypeTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ModelFamilyType}. */
@RestController
@RequestMapping("/api/model-family-types")
public class ModelFamilyTypeResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<ModelFamilyTypeTO, ModelFamilyTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogModelFamilyType";

  private static String APPLICATION_NAME = "model-catalog";

  public ModelFamilyTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
