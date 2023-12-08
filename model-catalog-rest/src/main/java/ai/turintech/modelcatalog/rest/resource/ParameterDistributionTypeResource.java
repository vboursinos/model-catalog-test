package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractCrudRestImpl;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.facade.ParameterDistributionTypeFacade;
import ai.turintech.modelcatalog.to.ParameterDistributionTypeTO;
import ai.turintech.modelcatalog.todtomapper.ParameterDistributionTypeMapper;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing {@link ParameterDistributionType}. */
@RestController
@RequestMapping("/api/parameter-distribution-types")
public class ParameterDistributionTypeResource
    extends ReactiveAbstractCrudRestImpl<
        ParameterDistributionTypeTO, ParameterDistributionTypeDTO, UUID> {
  private static final String ENTITY_NAME = "modelCatalogParameterDistributionType";

  private static String APPLICATION_NAME = "model-catalog";

  private final ParameterDistributionTypeFacade parameterDistributionTypeFacade;

  private final ParameterDistributionTypeMapper parameterDistributionTypeMapper;

  public ParameterDistributionTypeResource(
      ParameterDistributionTypeFacade parameterDistributionTypeFacade,
      ParameterDistributionTypeMapper parameterDistributionTypeMapper) {
    super(ENTITY_NAME, APPLICATION_NAME);
    this.parameterDistributionTypeFacade = parameterDistributionTypeFacade;
    this.parameterDistributionTypeMapper = parameterDistributionTypeMapper;
  }
}
