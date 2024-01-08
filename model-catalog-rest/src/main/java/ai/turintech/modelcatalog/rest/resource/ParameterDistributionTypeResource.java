package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.to.ParameterDistributionTypeTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing {@link ParameterDistributionType}. */
@RestController
@RequestMapping("/api/parameter-distribution-types")
public class ParameterDistributionTypeResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<
        ParameterDistributionTypeTO, ParameterDistributionTypeDTO> {
  private static final String ENTITY_NAME = "modelCatalogParameterDistributionType";

  private static String APPLICATION_NAME = "model-catalog";

  public ParameterDistributionTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
