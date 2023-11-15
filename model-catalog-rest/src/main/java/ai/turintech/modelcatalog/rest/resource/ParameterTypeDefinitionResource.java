package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import ai.turintech.modelcatalog.facade.ParameterTypeDefinitionFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
import ai.turintech.modelcatalog.todtomapper.ParameterTypeDefinitionMapper;
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

/** REST controller for managing {@link ParameterTypeDefinition}. */
@RestController
@RequestMapping("/api")
public class ParameterTypeDefinitionResource {

  private final Logger log = LoggerFactory.getLogger(ParameterTypeDefinitionResource.class);

  private static final String ENTITY_NAME = "modelCatalogParameterTypeDefinition";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private ParameterTypeDefinitionFacade parameterTypeDefinitionFacade;

  @Autowired private ParameterTypeDefinitionMapper parameterTypeDefinitionMapper;

  /**
   * {@code POST /parameter-type-definitions} : Create a new parameterTypeDefinition.
   *
   * @param parameterTypeDefinitionTO the parameterTypeDefinitionTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     parameterTypeDefinitionTO, or with status {@code 400 (Bad Request)} if the
   *     parameterTypeDefinition has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/parameter-type-definitions")
  public Mono<ResponseEntity<ParameterTypeDefinitionTO>> createParameterTypeDefinition(
      @Valid @RequestBody ParameterTypeDefinitionTO parameterTypeDefinitionTO)
      throws URISyntaxException {
    log.debug("REST request to save ParameterTypeDefinition : {}", parameterTypeDefinitionTO);
    if (parameterTypeDefinitionTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new parameterTypeDefinition cannot already have an ID", ENTITY_NAME, "idexists");
    }
    parameterTypeDefinitionTO.setId(UUID.randomUUID());
    return parameterTypeDefinitionFacade
        .save(parameterTypeDefinitionMapper.toDto(parameterTypeDefinitionTO))
        .map(parameterTypeDefinitionMapper::toTo)
        .map(
            result -> {
              try {
                return ResponseEntity.created(
                        new URI("/api/parameter-type-definitions/" + result.getId()))
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
   * {@code PUT /parameter-type-definitions/:id} : Updates an existing parameterTypeDefinition.
   *
   * @param id the id of the parameterTypeDefinitionTO to save.
   * @param parameterTypeDefinitionTO the parameterTypeDefinitionTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     parameterTypeDefinitionTO, or with status {@code 400 (Bad Request)} if the
   *     parameterTypeDefinitionTO is not valid, or with status {@code 500 (Internal Server Error)}
   *     if the parameterTypeDefinitionTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/parameter-type-definitions/{id}")
  public Mono<ResponseEntity<ParameterTypeDefinitionTO>> updateParameterTypeDefinition(
      @PathVariable(value = "id", required = false) final UUID id,
      @Valid @RequestBody ParameterTypeDefinitionTO parameterTypeDefinitionTO)
      throws URISyntaxException {
    log.debug(
        "REST request to update ParameterTypeDefinition : {}, {}", id, parameterTypeDefinitionTO);
    if (parameterTypeDefinitionTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, parameterTypeDefinitionTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return parameterTypeDefinitionFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              return parameterTypeDefinitionFacade
                  .update(parameterTypeDefinitionMapper.toDto(parameterTypeDefinitionTO))
                  .map(parameterTypeDefinitionMapper::toTo)
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
   * {@code PATCH /parameter-type-definitions/:id} : Partial updates given fields of an existing
   * parameterTypeDefinition, field will ignore if it is null
   *
   * @param id the id of the parameterTypeDefinitionTO to save.
   * @param parameterTypeDefinitionTO the parameterTypeDefinitionTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     parameterTypeDefinitionTO, or with status {@code 400 (Bad Request)} if the
   *     parameterTypeDefinitionTO is not valid, or with status {@code 404 (Not Found)} if the
   *     parameterTypeDefinitionTO is not found, or with status {@code 500 (Internal Server Error)}
   *     if the parameterTypeDefinitionTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/parameter-type-definitions/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<ParameterTypeDefinitionTO>> partialUpdateParameterTypeDefinition(
      @PathVariable(value = "id", required = false) final UUID id,
      @NotNull @RequestBody ParameterTypeDefinitionTO parameterTypeDefinitionTO)
      throws URISyntaxException {
    log.debug(
        "REST request to partial update ParameterTypeDefinition partially : {}, {}",
        id,
        parameterTypeDefinitionTO);
    if (parameterTypeDefinitionTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, parameterTypeDefinitionTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return parameterTypeDefinitionFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              Mono<ParameterTypeDefinitionTO> result =
                  parameterTypeDefinitionFacade
                      .partialUpdate(parameterTypeDefinitionMapper.toDto(parameterTypeDefinitionTO))
                      .map(parameterTypeDefinitionMapper::toTo);

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
   * {@code GET /parameter-type-definitions} : get all the parameterTypeDefinitions.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
   *     parameterTypeDefinitions in body.
   */
  @GetMapping(value = "/parameter-type-definitions", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<List<ParameterTypeDefinitionTO>> getAllParameterTypeDefinitions() {
    log.debug("REST request to get all ParameterTypeDefinitions");
    return parameterTypeDefinitionFacade
        .findAll()
        .collectList()
        .map(parameterTypeDefinitionMapper::toTo);
  }

  /**
   * {@code GET /parameter-type-definitions} : get all the parameterTypeDefinitions as a stream.
   *
   * @return the {@link Flux} of parameterTypeDefinitions.
   */
  @GetMapping(value = "/parameter-type-definitions", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<ParameterTypeDefinitionTO> getAllParameterTypeDefinitionsAsStream() {
    log.debug("REST request to get all ParameterTypeDefinitions as a stream");
    return parameterTypeDefinitionFacade.findAll().map(parameterTypeDefinitionMapper::toTo);
  }

  /**
   * {@code GET /parameter-type-definitions/:id} : get the "id" parameterTypeDefinition.
   *
   * @param id the id of the parameterTypeDefinitionTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *     parameterTypeDefinitionTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/parameter-type-definitions/{id}")
  public Mono<ResponseEntity<ParameterTypeDefinitionTO>> getParameterTypeDefinition(
      @PathVariable UUID id) {
    log.debug("REST request to get ParameterTypeDefinition : {}", id);
    Mono<ParameterTypeDefinitionTO> parameterTypeDefinitionTO =
        parameterTypeDefinitionFacade.findOne(id).map(parameterTypeDefinitionMapper::toTo);
    return ResponseUtil.wrapOrNotFound(parameterTypeDefinitionTO);
  }

  /**
   * {@code DELETE /parameter-type-definitions/:id} : delete the "id" parameterTypeDefinition.
   *
   * @param id the id of the parameterTypeDefinitionTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/parameter-type-definitions/{id}")
  public Mono<ResponseEntity<Void>> deleteParameterTypeDefinition(@PathVariable UUID id) {
    log.debug("REST request to delete ParameterTypeDefinition : {}", id);
    return parameterTypeDefinitionFacade
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
