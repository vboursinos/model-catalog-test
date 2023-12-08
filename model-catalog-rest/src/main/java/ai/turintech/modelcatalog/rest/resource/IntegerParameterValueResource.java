package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import ai.turintech.modelcatalog.facade.IntegerParameterValueFacade;
import ai.turintech.modelcatalog.to.IntegerParameterValueTO;
import ai.turintech.modelcatalog.todtomapper.IntegerParameterValueMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing {@link IntegerParameterValue}. */
@RestController
@RequestMapping("/api/integer-parameter-values")
public class IntegerParameterValueResource
    extends ReactiveAbstractCrudRestImpl<IntegerParameterValueTO, IntegerParameterValueDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogIntegerParameterValue";

  private static String APPLICATION_NAME = "model-catalog";

  private final IntegerParameterValueFacade integerParameterValueFacade;
  private final IntegerParameterValueMapper integerParameterValueMapper;

  public IntegerParameterValueResource(
      IntegerParameterValueFacade integerParameterValueFacade,
      IntegerParameterValueMapper integerParameterValueMapper) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.integerParameterValueFacade = integerParameterValueFacade;
    this.integerParameterValueMapper = integerParameterValueMapper;
  }
}
