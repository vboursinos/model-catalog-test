package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
import ai.turintech.modelcatalog.facade.MlTaskTypeFacade;
import ai.turintech.modelcatalog.to.MlTaskTypeTO;
import ai.turintech.modelcatalog.todtomapper.MlTaskTypeMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing {@link MlTaskType}. */
@RestController
@RequestMapping("/api/ml-task-types")
public class MlTaskTypeResource
    extends ReactiveAbstractCrudRestImpl<MlTaskTypeTO, MlTaskTypeDTO, UUID> {

  private final MlTaskTypeFacade mlTaskTypeFacade;

  private final MlTaskTypeMapper mlTaskTypeMapper;

  private static final String ENTITY_NAME = "modelCatalogMlTaskType";
  private static String APPLICATION_NAME = "model-catalog";

  public MlTaskTypeResource(MlTaskTypeFacade mlTaskTypeFacade, MlTaskTypeMapper mlTaskTypeMapper) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.mlTaskTypeFacade = mlTaskTypeFacade;
    this.mlTaskTypeMapper = mlTaskTypeMapper;
  }
}
