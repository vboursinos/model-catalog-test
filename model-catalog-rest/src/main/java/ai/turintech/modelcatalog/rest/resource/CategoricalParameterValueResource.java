package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import ai.turintech.modelcatalog.facade.CategoricalParameterValueFacade;
import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
import ai.turintech.modelcatalog.todtomapper.CategoricalParameterValueMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link CategoricalParameterValue}. */
@RestController
@RequestMapping("/api/categorical-parameter-values")
public class CategoricalParameterValueResource
    extends ReactiveAbstractCrudRestImpl<
        CategoricalParameterValueTO, CategoricalParameterValueDTO, UUID> {

  private static final String ENTITY_NAME = "modelCatalogCategoricalParameterValue";

  private static String APPLICATION_NAME = "model-catalog";

  private final CategoricalParameterValueFacade categoricalParameterValueFacade;

  private final CategoricalParameterValueMapper categoricalParameterValueMapper;

  public CategoricalParameterValueResource(
      CategoricalParameterValueFacade categoricalParameterValueFacade,
      CategoricalParameterValueMapper categoricalParameterValueMapper) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.categoricalParameterValueFacade = categoricalParameterValueFacade;
    this.categoricalParameterValueMapper = categoricalParameterValueMapper;
  }
}
