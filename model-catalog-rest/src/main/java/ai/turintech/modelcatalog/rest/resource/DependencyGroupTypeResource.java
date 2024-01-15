package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.DependencyGroupTypeDTO;
import ai.turintech.modelcatalog.to.DependencyGroupTypeTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing {@link ai.turintech.modelcatalog.entity.DependencyGroupType}. */
@RestController
@RequestMapping("/api/dependency-group-types")
public class DependencyGroupTypeResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<
        DependencyGroupTypeTO, DependencyGroupTypeDTO> {
  private static final String ENTITY_NAME = "modelCatalogDependencyGroupType";
  private static String APPLICATION_NAME = "model-catalog";

  public DependencyGroupTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
