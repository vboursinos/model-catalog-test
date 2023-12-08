package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import ai.turintech.modelcatalog.facade.ModelFamilyTypeFacade;
import ai.turintech.modelcatalog.to.ModelFamilyTypeTO;
import ai.turintech.modelcatalog.todtomapper.ModelFamilyTypeMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ModelFamilyType}. */
@RestController
@RequestMapping("/api/model-family-types")
public class ModelFamilyTypeResource
    extends ReactiveAbstractCrudRestImpl<ModelFamilyTypeTO, ModelFamilyTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogModelFamilyType";

  private static String APPLICATION_NAME = "model-catalog";

  private final ModelFamilyTypeFacade modelFamilyTypeFacade;

  private final ModelFamilyTypeMapper modelFamilyTypeMapper;

  public ModelFamilyTypeResource(
      ModelFamilyTypeFacade modelFamilyTypeFacade, ModelFamilyTypeMapper modelFamilyTypeMapper) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.modelFamilyTypeFacade = modelFamilyTypeFacade;
    this.modelFamilyTypeMapper = modelFamilyTypeMapper;
  }
}
