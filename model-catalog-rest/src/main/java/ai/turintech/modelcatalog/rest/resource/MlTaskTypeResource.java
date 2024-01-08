package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import ai.turintech.modelcatalog.to.MlTaskTypeTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing {@link MlTaskType}. */
@RestController
@RequestMapping("/api/ml-task-types")
public class MlTaskTypeResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<MlTaskTypeTO, MlTaskTypeDTO> {
  private static final String ENTITY_NAME = "modelCatalogMlTaskType";
  private static String APPLICATION_NAME = "model-catalog";

  public MlTaskTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
