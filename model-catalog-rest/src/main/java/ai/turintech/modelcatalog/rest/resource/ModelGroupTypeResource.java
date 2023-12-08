package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import ai.turintech.modelcatalog.facade.ModelGroupTypeFacade;
import ai.turintech.modelcatalog.to.ModelGroupTypeTO;
import ai.turintech.modelcatalog.todtomapper.ModelGroupTypeMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ModelGroupType}. */
@RestController
@RequestMapping("/api/model-group-types")
public class ModelGroupTypeResource
    extends ReactiveAbstractCrudRestImpl<ModelGroupTypeTO, ModelGroupTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogModelGroupType";
  private static String APPLICATION_NAME = "model-catalog";

  private final ModelGroupTypeFacade modelGroupTypeFacade;

  private final ModelGroupTypeMapper modelGroupTypeMapper;

  public ModelGroupTypeResource(
      ModelGroupTypeFacade modelGroupTypeFacade, ModelGroupTypeMapper modelGroupTypeMapper) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.modelGroupTypeFacade = modelGroupTypeFacade;
    this.modelGroupTypeMapper = modelGroupTypeMapper;
  }
}
