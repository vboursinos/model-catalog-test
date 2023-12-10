package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import ai.turintech.modelcatalog.to.ModelEnsembleTypeTO;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link ModelEnsembleType}. */
@RestController
@RequestMapping("/api/model-ensemble-types")
public class ModelEnsembleTypeResource
    extends ReactiveAbstractCrudRestImpl<ModelEnsembleTypeTO, ModelEnsembleTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogModelEnsembleType";

  private static String APPLICATION_NAME = "model-catalog";

  public ModelEnsembleTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
