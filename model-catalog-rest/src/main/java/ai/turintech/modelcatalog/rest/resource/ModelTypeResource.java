package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import ai.turintech.modelcatalog.facade.ModelTypeFacade;
import ai.turintech.modelcatalog.to.ModelTypeTO;
import ai.turintech.modelcatalog.todtomapper.ModelTypeMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ModelType}. */
@RestController
@RequestMapping("/api/model-types")
public class ModelTypeResource
    extends ReactiveAbstractCrudRestImpl<ModelTypeTO, ModelTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogModelType";
  private static String APPLICATION_NAME = "model-catalog";

  private final ModelTypeMapper modelTypeMapper;

  private final ModelTypeFacade modelTypeFacade;

  public ModelTypeResource(ModelTypeMapper modelTypeMapper, ModelTypeFacade modelTypeFacade) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.modelTypeMapper = modelTypeMapper;
    this.modelTypeFacade = modelTypeFacade;
  }
}
