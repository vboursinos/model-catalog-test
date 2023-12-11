package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import ai.turintech.modelcatalog.to.ModelGroupTypeTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ModelGroupType}. */
@RestController
@RequestMapping("/api/model-group-types")
public class ModelGroupTypeResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<ModelGroupTypeTO, ModelGroupTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogModelGroupType";
  private static String APPLICATION_NAME = "model-catalog";

  public ModelGroupTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
