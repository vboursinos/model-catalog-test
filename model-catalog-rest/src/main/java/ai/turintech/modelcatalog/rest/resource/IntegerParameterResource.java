package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.facade.IntegerParameterFacade;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.to.IntegerParameterTO;
import ai.turintech.modelcatalog.todtomapper.IntegerParameterMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/** REST controller for managing {@link IntegerParameter}. */
@RestController
@RequestMapping("/api/integer-parameters")
public class IntegerParameterResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<
        IntegerParameterTO, IntegerParameterDTO, UUID> {

  private final Logger log = LoggerFactory.getLogger(IntegerParameterResource.class);

  @Autowired private IntegerParameterFacade integerParameterFacade;
  @Autowired private IntegerParameterMapper integerParameterMapper;

  private static final String ENTITY_NAME = "modelCatalogIntegerParameter";
  private static String APPLICATION_NAME = "model-catalog";

  public IntegerParameterResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }

  /**
   * {@code POST /integer-parameters} : Create a new integerParameter.
   *
   * @param integerParameterTO the integerParameterDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     integerParameterDTO, or with status {@code 400 (Bad Request)} if the integerParameter has
   *     already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public Mono<ResponseEntity<IntegerParameterTO>> create(
      @RequestBody IntegerParameterTO integerParameterTO) {
    log.debug("REST request to save IntegerParameter : {}", integerParameterTO);
    return integerParameterFacade
        .save(integerParameterMapper.from(integerParameterTO))
        .map(integerParameterMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(new URI("/api/integer-parameters/" + result.getId()))
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
