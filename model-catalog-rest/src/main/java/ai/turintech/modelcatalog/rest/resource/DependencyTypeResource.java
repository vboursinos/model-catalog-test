package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.DependencyTypeDTO;
import ai.turintech.modelcatalog.to.DependencyTypeTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing {@link ai.turintech.modelcatalog.entity.DependencyType}. */
@RestController
@RequestMapping("/api/dependency-types")
public class DependencyTypeResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<DependencyTypeTO, DependencyTypeDTO> {
  private static final String ENTITY_NAME = "modelCatalogDependencyType";
  private static String APPLICATION_NAME = "model-catalog";

  public DependencyTypeResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }
}
