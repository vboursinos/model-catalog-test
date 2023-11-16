package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.facade.CategoricalParameterFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.CategoricalParameterTO;
import ai.turintech.modelcatalog.todtomapper.CategoricalParameterMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** REST controller for managing {@link CategoricalParameter}. */
@RestController
@RequestMapping("/api")
public class CategoricalParameterResource {

  private final Logger log = LoggerFactory.getLogger(CategoricalParameterResource.class);

  private static final String ENTITY_NAME = "modelCatalogCategoricalParameter";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private CategoricalParameterFacade categoricalParameterFacade;

  @Autowired private CategoricalParameterMapper categoricalParameterMapper;

  /**
   * {@code POST /categorical-parameters} : Create a new categoricalParameter.
   *
   * @param categoricalParameterTO the categoricalParameterDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     categoricalParameterDTO, or with status {@code 400 (Bad Request)} if the
   *     categoricalParameter has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/categorical-parameters")
  public Mono<ResponseEntity<CategoricalParameterTO>> createCategoricalParameter(
      @RequestBody CategoricalParameterTO categoricalParameterTO) throws URISyntaxException {
    log.debug("REST request to save CategoricalParameter : {}", categoricalParameterTO);
    if (categoricalParameterTO.getParameterTypeDefinitionId() != null) {
      throw new BadRequestAlertException(
          "A new categoricalParameter cannot already have an ID", ENTITY_NAME, "idexists");
    }
    return categoricalParameterFacade
        .save(categoricalParameterMapper.from(categoricalParameterTO))
        .map(categoricalParameterMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(
                        new URI(
                            "/api/categorical-parameters/" + result.getParameterTypeDefinitionId()))
                    .headers(
                        HeaderUtil.createEntityCreationAlert(
                            applicationName,
                            true,
                            ENTITY_NAME,
                            result.getParameterTypeDefinitionId().toString()))
                    .body(result);
              } catch (URISyntaxException e) {
                throw new RuntimeException(e);
              }
            });
  }

  /**
   * {@code PUT /categorical-parameters/:id} : Updates an existing categoricalParameter.
   *
   * @param id the id of the categoricalParameterDTO to save.
   * @param categoricalParameterTO the categoricalParameterDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     categoricalParameterDTO, or with status {@code 400 (Bad Request)} if the
   *     categoricalParameterDTO is not valid, or with status {@code 500 (Internal Server Error)} if
   *     the categoricalParameterDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/categorical-parameters/{id}")
  public Mono<ResponseEntity<CategoricalParameterTO>> updateCategoricalParameter(
      @PathVariable(value = "id", required = false) final UUID id,
      @RequestBody CategoricalParameterTO categoricalParameterTO)
      throws URISyntaxException {
    log.debug("REST request to update CategoricalParameter : {}, {}", id, categoricalParameterTO);
    if (categoricalParameterTO.getParameterTypeDefinitionId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, categoricalParameterTO.getParameterTypeDefinitionId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return categoricalParameterFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              return categoricalParameterFacade
                  .update(categoricalParameterMapper.from(categoricalParameterTO))
                  .map(categoricalParameterMapper::to)
                  .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                  .map(
                      result ->
                          ResponseEntity.ok()
                              .headers(
                                  HeaderUtil.createEntityUpdateAlert(
                                      applicationName,
                                      true,
                                      ENTITY_NAME,
                                      result.getParameterTypeDefinitionId().toString()))
                              .body(result));
            });
  }

  /**
   * {@code PATCH /categorical-parameters/:id} : Partial updates given fields of an existing
   * categoricalParameter, field will ignore if it is null
   *
   * @param id the id of the categoricalParameterDTO to save.
   * @param categoricalParameterTO the categoricalParameterDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     categoricalParameterDTO, or with status {@code 400 (Bad Request)} if the
   *     categoricalParameterDTO is not valid, or with status {@code 404 (Not Found)} if the
   *     categoricalParameterDTO is not found, or with status {@code 500 (Internal Server Error)} if
   *     the categoricalParameterDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/categorical-parameters/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<CategoricalParameterTO>> partialUpdateCategoricalParameter(
      @PathVariable(value = "id", required = false) final UUID id,
      @RequestBody CategoricalParameterTO categoricalParameterTO)
      throws URISyntaxException {
    log.debug(
        "REST request to partial update CategoricalParameter partially : {}, {}",
        id,
        categoricalParameterTO);
    if (categoricalParameterTO.getParameterTypeDefinitionId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, categoricalParameterTO.getParameterTypeDefinitionId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return categoricalParameterFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              Mono<CategoricalParameterTO> result =
                  categoricalParameterFacade
                      .partialUpdate(categoricalParameterMapper.from(categoricalParameterTO))
                      .map(categoricalParameterMapper::to);

              return result
                  .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                  .map(
                      res ->
                          ResponseEntity.ok()
                              .headers(
                                  HeaderUtil.createEntityUpdateAlert(
                                      applicationName,
                                      true,
                                      ENTITY_NAME,
                                      res.getParameterTypeDefinitionId().toString()))
                              .body(res));
            });
  }

  /**
   * {@code GET /categorical-parameters} : get all the categoricalParameters.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
   *     categoricalParameters in body.
   */
  @GetMapping(value = "/categorical-parameters", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<List<CategoricalParameterDTO>> getAllCategoricalParameters() {
    log.debug("REST request to get all CategoricalParameters");
    return categoricalParameterFacade.findAll().collectList();
  }

  /**
   * {@code GET /categorical-parameters} : get all the categoricalParameters as a stream.
   *
   * @return the {@link Flux} of categoricalParameters.
   */
  @GetMapping(value = "/categorical-parameters", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<CategoricalParameterTO> getAllCategoricalParametersAsStream() {
    log.debug("REST request to get all CategoricalParameters as a stream");
    return categoricalParameterFacade.findAll().map(categoricalParameterMapper::to);
  }

  /**
   * {@code GET /categorical-parameters/:id} : get the "id" categoricalParameter.
   *
   * @param id the id of the categoricalParameterDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *     categoricalParameterDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/categorical-parameters/{id}")
  public Mono<ResponseEntity<CategoricalParameterTO>> getCategoricalParameter(
      @PathVariable UUID id) {
    log.debug("REST request to get CategoricalParameter : {}", id);
    Mono<CategoricalParameterTO> categoricalParameterTO =
        categoricalParameterFacade.findOne(id).map(categoricalParameterMapper::to);
    return ResponseUtil.wrapOrNotFound(categoricalParameterTO);
  }

  /**
   * {@code DELETE /categorical-parameters/:id} : delete the "id" categoricalParameter.
   *
   * @param id the id of the categoricalParameterDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/categorical-parameters/{id}")
  public Mono<ResponseEntity<Void>> deleteCategoricalParameter(@PathVariable UUID id) {
    log.debug("REST request to delete CategoricalParameter : {}", id);
    return categoricalParameterFacade
        .delete(id)
        .then(
            Mono.just(
                ResponseEntity.noContent()
                    .headers(
                        HeaderUtil.createEntityDeletionAlert(
                            applicationName, true, ENTITY_NAME, id.toString()))
                    .build()));
  }
}
