package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.entity.ModelStructureType;
import ai.turintech.modelcatalog.facade.ModelStructureTypeFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.ModelStructureTypeTO;
import ai.turintech.modelcatalog.todtomapper.ModelStructureTypeMapper;
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

/** REST controller for managing {@link ModelStructureType}. */
@RestController
@RequestMapping("/api")
public class ModelStructureTypeResource {

  private final Logger log = LoggerFactory.getLogger(ModelStructureTypeResource.class);

  private static final String ENTITY_NAME = "modelCatalogModelStructureType";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private ModelStructureTypeFacade modelStructureTypeFacade;

  @Autowired private ModelStructureTypeMapper modelStructureTypeMapper;

  /**
   * {@code POST /model-structure-types} : Create a new modelStructureType.
   *
   * @param modelStructureTypeTO the ModelStructureTypeTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     ModelStructureTypeTO, or with status {@code 400 (Bad Request)} if the modelStructureType
   *     has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/model-structure-types")
  public Mono<ResponseEntity<ModelStructureTypeTO>> createModelStructureType(
      @Valid @RequestBody ModelStructureTypeTO modelStructureTypeTO) throws URISyntaxException {
    log.debug("REST request to save ModelStructureType : {}", modelStructureTypeTO);
    if (modelStructureTypeTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new modelStructureType cannot already have an ID", ENTITY_NAME, "idexists");
    }
    modelStructureTypeTO.setId(UUID.randomUUID());
    return modelStructureTypeFacade
        .save(modelStructureTypeMapper.toDto(modelStructureTypeTO))
        .map(modelStructureTypeMapper::toTo)
        .map(
            result -> {
              try {
                return ResponseEntity.created(
                        new URI("/api/model-structure-types/" + result.getId()))
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
   * {@code PUT /model-structure-types/:id} : Updates an existing modelStructureType.
   *
   * @param id the id of the ModelStructureTypeTO to save.
   * @param modelStructureTypeTO the ModelStructureTypeTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     ModelStructureTypeTO, or with status {@code 400 (Bad Request)} if the ModelStructureTypeTO
   *     is not valid, or with status {@code 500 (Internal Server Error)} if the
   *     ModelStructureTypeTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/model-structure-types/{id}")
  public Mono<ResponseEntity<ModelStructureTypeTO>> updateModelStructureType(
      @PathVariable(value = "id", required = false) final UUID id,
      @Valid @RequestBody ModelStructureTypeTO modelStructureTypeTO)
      throws URISyntaxException {
    log.debug("REST request to update ModelStructureType : {}, {}", id, modelStructureTypeTO);
    if (modelStructureTypeTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, modelStructureTypeTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return modelStructureTypeFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              return modelStructureTypeFacade
                  .update(modelStructureTypeMapper.toDto(modelStructureTypeTO))
                  .map(modelStructureTypeMapper::toTo)
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
   * {@code PATCH /model-structure-types/:id} : Partial updates given fields of an existing
   * modelStructureType, field will ignore if it is null
   *
   * @param id the id of the ModelStructureTypeTO to save.
   * @param modelStructureTypeTO the ModelStructureTypeTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     ModelStructureTypeTO, or with status {@code 400 (Bad Request)} if the ModelStructureTypeTO
   *     is not valid, or with status {@code 404 (Not Found)} if the ModelStructureTypeTO is not
   *     found, or with status {@code 500 (Internal Server Error)} if the ModelStructureTypeTO
   *     couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/model-structure-types/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<ModelStructureTypeTO>> partialUpdateModelStructureType(
      @PathVariable(value = "id", required = false) final UUID id,
      @NotNull @RequestBody ModelStructureTypeTO modelStructureTypeTO)
      throws URISyntaxException {
    log.debug(
        "REST request to partial update ModelStructureType partially : {}, {}",
        id,
        modelStructureTypeTO);
    if (modelStructureTypeTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, modelStructureTypeTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return modelStructureTypeFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              Mono<ModelStructureTypeTO> result =
                  modelStructureTypeFacade
                      .partialUpdate(modelStructureTypeMapper.toDto(modelStructureTypeTO))
                      .map(modelStructureTypeMapper::toTo);

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
   * {@code GET /model-structure-types} : get all the modelStructureTypes.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
   *     modelStructureTypes in body.
   */
  @GetMapping(value = "/model-structure-types", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<List<ModelStructureTypeTO>> getAllModelStructureTypes() {
    log.debug("REST request to get all ModelStructureTypes");
    return modelStructureTypeFacade.findAll().collectList().map(modelStructureTypeMapper::toTo);
  }

  /**
   * {@code GET /model-structure-types} : get all the modelStructureTypes as a stream.
   *
   * @return the {@link Flux} of modelStructureTypes.
   */
  @GetMapping(value = "/model-structure-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<ModelStructureTypeTO> getAllModelStructureTypesAsStream() {
    log.debug("REST request to get all ModelStructureTypes as a stream");
    return modelStructureTypeFacade.findAll().map(modelStructureTypeMapper::toTo);
  }

  /**
   * {@code GET /model-structure-types/:id} : get the "id" modelStructureType.
   *
   * @param id the id of the ModelStructureTypeTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *     ModelStructureTypeTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/model-structure-types/{id}")
  public Mono<ResponseEntity<ModelStructureTypeTO>> getModelStructureType(@PathVariable UUID id) {
    log.debug("REST request to get ModelStructureType : {}", id);
    Mono<ModelStructureTypeTO> modelStructureTypeTO =
        modelStructureTypeFacade.findOne(id).map(modelStructureTypeMapper::toTo);
    return ResponseUtil.wrapOrNotFound(modelStructureTypeTO);
  }

  /**
   * {@code DELETE /model-structure-types/:id} : delete the "id" modelStructureType.
   *
   * @param id the id of the ModelStructureTypeTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/model-structure-types/{id}")
  public Mono<ResponseEntity<Void>> deleteModelStructureType(@PathVariable UUID id) {
    log.debug("REST request to delete ModelStructureType : {}", id);
    return modelStructureTypeFacade
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
