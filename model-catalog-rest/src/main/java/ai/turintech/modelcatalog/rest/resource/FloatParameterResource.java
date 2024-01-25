package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.facade.FloatParameterFacade;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.to.FloatParameterTO;
import ai.turintech.modelcatalog.todtomapper.FloatParameterMapper;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/** REST controller for managing {@link FloatParameter}. */
@RestController
@RequestMapping("/api/float-parameters")
public class FloatParameterResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<FloatParameterTO, FloatParameterDTO> {

  private final Logger log = LoggerFactory.getLogger(FloatParameterResource.class);
  @Autowired private FloatParameterFacade floatParameterFacade;
  @Autowired private FloatParameterMapper floatParameterMapper;

  private static final String ENTITY_NAME = "modelCatalogFloatParameter";

  private static String APPLICATION_NAME = "model-catalog";

  public FloatParameterResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }

  /**
   * {@code POST /float-parameters} : Create a new floatParameter.
   *
   * @param floatParameterTO the floatParameterDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     floatParameterDTO, or with status {@code 400 (Bad Request)} if the floatParameter has
   *     already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @Override
  @PostMapping("")
  public Mono<ResponseEntity<FloatParameterTO>> create(
      @RequestBody FloatParameterTO floatParameterTO) {
    log.debug("REST request to save FloatParameter : {}", floatParameterTO);
    return floatParameterFacade
        .save(floatParameterMapper.from(floatParameterTO))
        .map(floatParameterMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(new URI("/api/float-parameters/" + result.getId()))
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
