package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.facade.ParameterTypeFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.ParameterTypeTO;
import ai.turintech.modelcatalog.todtomapper.ParameterTypeMapper;
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

/** REST controller for managing {@link ParameterType}. */
@RestController
@RequestMapping("/api")
public class ParameterTypeResource {

  private final Logger log = LoggerFactory.getLogger(ParameterTypeResource.class);

  private static final String ENTITY_NAME = "modelCatalogParameterType";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private ParameterTypeFacade parameterTypeFacade;

  @Autowired private ParameterTypeMapper parameterTypeMapper;

  /**
   * {@code POST /parameter-types} : Create a new parameterType.
   *
   * @param parameterTypeTO the parameterTypeTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     parameterTypeTO, or with status {@code 400 (Bad Request)} if the parameterType has already
   *     an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/parameter-types")
  public Mono<ResponseEntity<ParameterTypeTO>> createParameterType(
      @Valid @RequestBody ParameterTypeTO parameterTypeTO) throws URISyntaxException {
    log.debug("REST request to save ParameterType : {}", parameterTypeTO);
    if (parameterTypeTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new parameterType cannot already have an ID", ENTITY_NAME, "idexists");
    }
    parameterTypeTO.setId(UUID.randomUUID());
    return parameterTypeFacade
        .save(parameterTypeMapper.toDto(parameterTypeTO))
        .map(parameterTypeMapper::toTo)
        .map(
            result -> {
              try {
                return ResponseEntity.created(new URI("/api/parameter-types/" + result.getId()))
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
   * {@code PUT /parameter-types/:id} : Updates an existing parameterType.
   *
   * @param id the id of the parameterTypeTO to save.
   * @param parameterTypeTO the parameterTypeTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     parameterTypeTO, or with status {@code 400 (Bad Request)} if the parameterTypeTO is not
   *     valid, or with status {@code 500 (Internal Server Error)} if the parameterTypeTO couldn't
   *     be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/parameter-types/{id}")
  public Mono<ResponseEntity<ParameterTypeTO>> updateParameterType(
      @PathVariable(value = "id", required = false) final UUID id,
      @Valid @RequestBody ParameterTypeTO parameterTypeTO)
      throws URISyntaxException {
    log.debug("REST request to update ParameterType : {}, {}", id, parameterTypeTO);
    if (parameterTypeTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, parameterTypeTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return parameterTypeFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              return parameterTypeFacade
                  .update(parameterTypeMapper.toDto(parameterTypeTO))
                  .map(parameterTypeMapper::toTo)
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
   * {@code PATCH /parameter-types/:id} : Partial updates given fields of an existing parameterType,
   * field will ignore if it is null
   *
   * @param id the id of the parameterTypeTO to save.
   * @param parameterTypeTO the parameterTypeTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     parameterTypeTO, or with status {@code 400 (Bad Request)} if the parameterTypeTO is not
   *     valid, or with status {@code 404 (Not Found)} if the parameterTypeTO is not found, or with
   *     status {@code 500 (Internal Server Error)} if the parameterTypeTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/parameter-types/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<ParameterTypeTO>> partialUpdateParameterType(
      @PathVariable(value = "id", required = false) final UUID id,
      @NotNull @RequestBody ParameterTypeTO parameterTypeTO)
      throws URISyntaxException {
    log.debug(
        "REST request to partial update ParameterType partially : {}, {}", id, parameterTypeTO);
    if (parameterTypeTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, parameterTypeTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return parameterTypeFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              Mono<ParameterTypeTO> result =
                  parameterTypeFacade
                      .partialUpdate(parameterTypeMapper.toDto(parameterTypeTO))
                      .map(parameterTypeMapper::toTo);

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
   * {@code GET /parameter-types} : get all the parameterTypes.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameterTypes
   *     in body.
   */
  @GetMapping(value = "/parameter-types", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<List<ParameterTypeTO>> getAllParameterTypes() {
    log.debug("REST request to get all ParameterTypes");
    return parameterTypeFacade.findAll().collectList().map(parameterTypeMapper::toTo);
  }

  /**
   * {@code GET /parameter-types} : get all the parameterTypes as a stream.
   *
   * @return the {@link Flux} of parameterTypes.
   */
  @GetMapping(value = "/parameter-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<ParameterTypeTO> getAllParameterTypesAsStream() {
    log.debug("REST request to get all ParameterTypes as a stream");
    return parameterTypeFacade.findAll().map(parameterTypeMapper::toTo);
  }

  /**
   * {@code GET /parameter-types/:id} : get the "id" parameterType.
   *
   * @param id the id of the parameterTypeTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *     parameterTypeTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/parameter-types/{id}")
  public Mono<ResponseEntity<ParameterTypeTO>> getParameterType(@PathVariable UUID id) {
    log.debug("REST request to get ParameterType : {}", id);
    Mono<ParameterTypeTO> parameterTypeTO =
        parameterTypeFacade.findOne(id).map(parameterTypeMapper::toTo);
    return ResponseUtil.wrapOrNotFound(parameterTypeTO);
  }

  /**
   * {@code DELETE /parameter-types/:id} : delete the "id" parameterType.
   *
   * @param id the id of the parameterTypeTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/parameter-types/{id}")
  public Mono<ResponseEntity<Void>> deleteParameterType(@PathVariable UUID id) {
    log.debug("REST request to delete ParameterType : {}", id);
    return parameterTypeFacade
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
