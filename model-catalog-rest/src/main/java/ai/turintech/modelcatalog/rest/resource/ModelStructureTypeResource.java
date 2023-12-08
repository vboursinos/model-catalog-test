package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import ai.turintech.modelcatalog.facade.ModelStructureTypeFacade;
import ai.turintech.modelcatalog.to.ModelStructureTypeTO;
import ai.turintech.modelcatalog.todtomapper.ModelStructureTypeMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing {@link ModelStructureType}. */
@RestController
@RequestMapping("/api/model-structure-types")
public class ModelStructureTypeResource
    extends ReactiveAbstractCrudRestImpl<ModelStructureTypeTO, ModelStructureTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogModelStructureType";

  private static String APPLICATION_NAME = "model-catalog";

  private final ModelStructureTypeFacade modelStructureTypeFacade;

  private final ModelStructureTypeMapper modelStructureTypeMapper;

  public ModelStructureTypeResource(
      ModelStructureTypeFacade modelStructureTypeFacade,
      ModelStructureTypeMapper modelStructureTypeMapper) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.modelStructureTypeFacade = modelStructureTypeFacade;
    this.modelStructureTypeMapper = modelStructureTypeMapper;
  }
}
