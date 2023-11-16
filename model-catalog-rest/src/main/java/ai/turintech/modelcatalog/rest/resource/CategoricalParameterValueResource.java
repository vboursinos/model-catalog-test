package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import ai.turintech.modelcatalog.facade.CategoricalParameterValueFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
import ai.turintech.modelcatalog.todtomapper.CategoricalParameterValueMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

/** REST controller for managing {@link CategoricalParameterValue}. */
@RestController
@RequestMapping("/api")
public class CategoricalParameterValueResource {

  private final Logger log = LoggerFactory.getLogger(CategoricalParameterValueResource.class);

  private static final String ENTITY_NAME = "modelCatalogCategoricalParameterValue";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private CategoricalParameterValueFacade categoricalParameterValueFacade;

  @Autowired private CategoricalParameterValueMapper categoricalParameterValueMapper;
  /**
   * {@code POST /categorical-parameter-values} : Create a new categoricalParameterValue.
   *
   * @param categoricalParameterValueTO the categoricalParameterValueDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     categoricalParameterValueDTO, or with status {@code 400 (Bad Request)} if the
   *     categoricalParameterValue has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/categorical-parameter-values")
  public Mono<ResponseEntity<CategoricalParameterValueTO>> createCategoricalParameterValue(
      @Valid @RequestBody CategoricalParameterValueTO categoricalParameterValueTO)
      throws URISyntaxException {
    log.debug("REST request to save CategoricalParameterValue : {}", categoricalParameterValueTO);
    if (categoricalParameterValueTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new categoricalParameterValue cannot already have an ID", ENTITY_NAME, "idexists");
    }
    return categoricalParameterValueFacade
        .save(categoricalParameterValueMapper.from(categoricalParameterValueTO))
        .map(categoricalParameterValueMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(
                        new URI("/api/categorical-parameter-values/" + result.getId()))
                    .headers(
                        HeaderUtil.createEntityCreationAlert(
                            applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
              } catch (URISyntaxException e) {
                throw new RuntimeException(e);
              }
            });
  }

  /**
   * {@code PUT /categorical-parameter-values/:id} : Updates an existing categoricalParameterValue.
   *
   * @param id the id of the categoricalParameterValueDTO to save.
   * @param categoricalParameterValueTO the categoricalParameterValueDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     categoricalParameterValueDTO, or with status {@code 400 (Bad Request)} if the
   *     categoricalParameterValueDTO is not valid, or with status {@code 500 (Internal Server
   *     Error)} if the categoricalParameterValueDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/categorical-parameter-values/{id}")
  public Mono<ResponseEntity<CategoricalParameterValueTO>> updateCategoricalParameterValue(
      @PathVariable(value = "id", required = false) final UUID id,
      @Valid @RequestBody CategoricalParameterValueTO categoricalParameterValueTO)
      throws URISyntaxException {
    log.debug(
        "REST request to update CategoricalParameterValue : {}, {}",
        id,
        categoricalParameterValueTO);
    if (categoricalParameterValueTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, categoricalParameterValueTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return categoricalParameterValueFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              return categoricalParameterValueFacade
                  .update(categoricalParameterValueMapper.from(categoricalParameterValueTO))
                  .map(categoricalParameterValueMapper::to)
                  .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                  .map(
                      result ->
                          ResponseEntity.ok()
                              .headers(
                                  HeaderUtil.createEntityUpdateAlert(
                                      applicationName,
                                      true,
                                      ENTITY_NAME,
                                      result.getId().toString()))
                              .body(result));
            });
  }

  /**
   * {@code PATCH /categorical-parameter-values/:id} : Partial updates given fields of an existing
   * categoricalParameterValue, field will ignore if it is null
   *
   * @param id the id of the categoricalParameterValueDTO to save.
   * @param categoricalParameterValueTO the categoricalParameterValueDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     categoricalParameterValueDTO, or with status {@code 400 (Bad Request)} if the
   *     categoricalParameterValueDTO is not valid, or with status {@code 404 (Not Found)} if the
   *     categoricalParameterValueDTO is not found, or with status {@code 500 (Internal Server
   *     Error)} if the categoricalParameterValueDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/categorical-parameter-values/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<CategoricalParameterValueTO>> partialUpdateCategoricalParameterValue(
      @PathVariable(value = "id", required = false) final UUID id,
      @NotNull @RequestBody CategoricalParameterValueTO categoricalParameterValueTO)
      throws URISyntaxException {
    log.debug(
        "REST request to partial update CategoricalParameterValue partially : {}, {}",
        id,
        categoricalParameterValueTO);
    if (categoricalParameterValueTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, categoricalParameterValueTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return categoricalParameterValueFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              Mono<CategoricalParameterValueTO> result =
                  categoricalParameterValueFacade
                      .partialUpdate(
                          categoricalParameterValueMapper.from(categoricalParameterValueTO))
                      .map(categoricalParameterValueMapper::to);

              return result
                  .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                  .map(
                      res ->
                          ResponseEntity.ok()
                              .headers(
                                  HeaderUtil.createEntityUpdateAlert(
                                      applicationName, true, ENTITY_NAME, res.getId().toString()))
                              .body(res));
            });
  }

  /**
   * {@code GET /categorical-parameter-values} : get all the categoricalParameterValues.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
   *     categoricalParameterValues in body.
   */
  @GetMapping(value = "/categorical-parameter-values", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<List<CategoricalParameterValueTO>> getAllCategoricalParameterValues() {
    log.debug("REST request to get all CategoricalParameterValues");
    return categoricalParameterValueFacade
        .findAll()
        .collectList()
        .map(categoricalParameterValueMapper::toTO);
  }

  /**
   * {@code GET /categorical-parameter-values} : get all the categoricalParameterValues as a stream.
   *
   * @return the {@link Flux} of categoricalParameterValues.
   */
  @GetMapping(
      value = "/categorical-parameter-values",
      produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<CategoricalParameterValueTO> getAllCategoricalParameterValuesAsStream() {
    log.debug("REST request to get all CategoricalParameterValues as a stream");
    return categoricalParameterValueFacade.findAll().map(categoricalParameterValueMapper::to);
  }

  /**
   * {@code GET /categorical-parameter-values/:id} : get the "id" categoricalParameterValue.
   *
   * @param id the id of the categoricalParameterValueDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *     categoricalParameterValueDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/categorical-parameter-values/{id}")
  public Mono<ResponseEntity<CategoricalParameterValueTO>> getCategoricalParameterValue(
      @PathVariable UUID id) {
    log.debug("REST request to get CategoricalParameterValue : {}", id);
    Mono<CategoricalParameterValueTO> categoricalParameterValueTO =
        categoricalParameterValueFacade.findOne(id).map(categoricalParameterValueMapper::to);
    return ResponseUtil.wrapOrNotFound(categoricalParameterValueTO);
  }

  /**
   * {@code DELETE /categorical-parameter-values/:id} : delete the "id" categoricalParameterValue.
   *
   * @param id the id of the categoricalParameterValueDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/categorical-parameter-values/{id}")
  public Mono<ResponseEntity<Void>> deleteCategoricalParameterValue(@PathVariable UUID id) {
    log.debug("REST request to delete CategoricalParameterValue : {}", id);
    return categoricalParameterValueFacade
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
