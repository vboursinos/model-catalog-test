package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.facade.CategoricalParameterFacade;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.to.CategoricalParameterTO;
import ai.turintech.modelcatalog.todtomapper.CategoricalParameterMapper;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/** REST controller for managing {@link CategoricalParameter}. */
@RestController
@RequestMapping("/api/categorical-parameters")
public class CategoricalParameterResource
    extends ReactiveAbstractUUIDIdentityCrudRestImpl<
        CategoricalParameterTO, CategoricalParameterDTO> {

  private final Logger log = LoggerFactory.getLogger(CategoricalParameterResource.class);

  @Autowired private CategoricalParameterFacade categoricalParameterFacade;

  @Autowired private CategoricalParameterMapper categoricalParameterMapper;

  private static final String ENTITY_NAME = "modelCatalogCategoricalParameter";

  private static String APPLICATION_NAME = "model-catalog";

  public CategoricalParameterResource() {
    super(ENTITY_NAME, APPLICATION_NAME);
  }

  /**
   * {@code POST /categorical-parameters} : Create a new categoricalParameter.
   *
   * @param categoricalParameterTO the categoricalParameterDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     categoricalParameterDTO, or with status {@code 400 (Bad Request)} if the
   *     categoricalParameter has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @Override
  @PostMapping()
  public Mono<ResponseEntity<CategoricalParameterTO>> create(
      @RequestBody CategoricalParameterTO categoricalParameterTO) {
    log.debug("REST request to save CategoricalParameter : {}", categoricalParameterTO);
    return categoricalParameterFacade
        .save(categoricalParameterMapper.from(categoricalParameterTO))
        .map(categoricalParameterMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(
                        new URI("/api/categorical-parameters/" + result.getId()))
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
