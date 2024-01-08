package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.facade.BooleanParameterFacade;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.to.BooleanParameterTO;
import ai.turintech.modelcatalog.todtomapper.BooleanParameterMapper;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/** REST controller for managing {@link BooleanParameter}. */
@RestController
@RequestMapping("/api/boolean-parameters")
public class BooleanParameterResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<BooleanParameterTO, BooleanParameterDTO> {
  private final Logger log = LoggerFactory.getLogger(IntegerParameterResource.class);

  @Autowired private BooleanParameterFacade booleanParameterFacade;

  @Autowired private BooleanParameterMapper booleanParameterMapper;

  private static final String ENTITY_NAME = "modelCatalogBooleanParameter";

  private static String APPLICATION_NAME = "model-catalog";

  public BooleanParameterResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }

  /**
   * {@code POST /boolean-parameters} : Create a new booleanParameter.
   *
   * @param booleanParameterTO the booleanParameterDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     booleanParameterDTO, or with status {@code 400 (Bad Request)} if the booleanParameter has
   *     already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @Override
  @PostMapping("")
  public Mono<ResponseEntity<BooleanParameterTO>> create(
      @RequestBody BooleanParameterTO booleanParameterTO) {
    log.debug("REST request to save BooleanParameter : {}", booleanParameterTO);
    return booleanParameterFacade
        .save(this.booleanParameterMapper.from(booleanParameterTO))
        .map(booleanParameterMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(new URI("/api/boolean-parameters/" + result.getId()))
                    .headers(
                        HeaderUtil.createEntityCreationAlert(
                            applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
              } catch (URISyntaxException e) {
                throw new RuntimeException(e);
              }
            });
  }
}
