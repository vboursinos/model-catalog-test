package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.facade.FloatParameterFacade;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.FloatParameterTO;
import ai.turintech.modelcatalog.todtomapper.FloatParameterMapper;
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

/** REST controller for managing {@link FloatParameter}. */
@RestController
@RequestMapping("/api")
public class FloatParameterResource {

  private final Logger log = LoggerFactory.getLogger(FloatParameterResource.class);

  private static final String ENTITY_NAME = "modelCatalogFloatParameter";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private FloatParameterFacade floatParameterFacade;
  @Autowired private FloatParameterMapper floatParameterMapper;

  /**
   * {@code POST /float-parameters} : Create a new floatParameter.
   *
   * @param floatParameterTO the floatParameterDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     floatParameterDTO, or with status {@code 400 (Bad Request)} if the floatParameter has
   *     already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/float-parameters")
  public Mono<ResponseEntity<FloatParameterTO>> createFloatParameter(
      @RequestBody FloatParameterTO floatParameterTO) {
    log.debug("REST request to save FloatParameter : {}", floatParameterTO);
    if (floatParameterTO.getParameterTypeDefinitionId() != null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "A new floatParameter cannot already have an ID");
    }
    return floatParameterFacade
        .save(floatParameterMapper.from(floatParameterTO))
        .map(floatParameterMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(
                        new URI("/api/float-parameters/" + result.getParameterTypeDefinitionId()))
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
   * {@code PUT /float-parameters/:id} : Updates an existing floatParameter.
   *
   * @param id the id of the floatParameterDTO to save.
   * @param floatParameterTO the floatParameterDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     floatParameterDTO, or with status {@code 400 (Bad Request)} if the floatParameterDTO is not
   *     valid, or with status {@code 500 (Internal Server Error)} if the floatParameterDTO couldn't
   *     be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/float-parameters/{id}")
  public Mono<ResponseEntity<FloatParameterTO>> updateFloatParameter(
      @PathVariable(value = "id", required = false) final UUID id,
      @RequestBody FloatParameterTO floatParameterTO) {
    log.debug("REST request to update FloatParameter : {}, {}", id, floatParameterTO);
    if (floatParameterTO.getParameterTypeDefinitionId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
    }
    if (!Objects.equals(id, floatParameterTO.getParameterTypeDefinitionId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
    }

    return floatParameterFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
              }

              return floatParameterFacade
                  .update(floatParameterMapper.from(floatParameterTO))
                  .map(floatParameterMapper::to)
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
   * {@code PATCH /float-parameters/:id} : Partial updates given fields of an existing
   * floatParameter, field will ignore if it is null
   *
   * @param id the id of the floatParameterDTO to save.
   * @param floatParameterTO the floatParameterDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     floatParameterDTO, or with status {@code 400 (Bad Request)} if the floatParameterDTO is not
   *     valid, or with status {@code 404 (Not Found)} if the floatParameterDTO is not found, or
   *     with status {@code 500 (Internal Server Error)} if the floatParameterDTO couldn't be
   *     updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/float-parameters/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<FloatParameterTO>> partialUpdateFloatParameter(
      @PathVariable(value = "id", required = false) final UUID id,
      @RequestBody FloatParameterTO floatParameterTO) {
    log.debug(
        "REST request to partial update FloatParameter partially : {}, {}", id, floatParameterTO);
    if (floatParameterTO.getParameterTypeDefinitionId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
    }
    if (!Objects.equals(id, floatParameterTO.getParameterTypeDefinitionId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
    }

    return floatParameterFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
              }

              Mono<FloatParameterTO> result =
                  floatParameterFacade
                      .partialUpdate(floatParameterMapper.from(floatParameterTO))
                      .map(floatParameterMapper::to);

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
   * {@code GET /float-parameters} : get all the floatParameters.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of floatParameters
   *     in body.
   */
  @GetMapping(value = "/float-parameters", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<List<FloatParameterTO>> getAllFloatParameters() {
    log.debug("REST request to get all FloatParameters");
    return floatParameterFacade.findAll().collectList().map(floatParameterMapper::toTO);
  }

  /**
   * {@code GET /float-parameters} : get all the floatParameters as a stream.
   *
   * @return the {@link Flux} of floatParameters.
   */
  @GetMapping(value = "/float-parameters", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<FloatParameterTO> getAllFloatParametersAsStream() {
    log.debug("REST request to get all FloatParameters as a stream");
    return floatParameterFacade.findAll().map(floatParameterMapper::to);
  }

  /**
   * {@code GET /float-parameters/:id} : get the "id" floatParameter.
   *
   * @param id the id of the floatParameterDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *     floatParameterDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/float-parameters/{id}")
  public Mono<ResponseEntity<FloatParameterTO>> getFloatParameter(@PathVariable UUID id) {
    log.debug("REST request to get FloatParameter : {}", id);
    Mono<FloatParameterTO> floatParameterTO =
        floatParameterFacade.findOne(id).map(floatParameterMapper::to);
    return ResponseUtil.wrapOrNotFound(floatParameterTO);
  }

  /**
   * {@code DELETE /float-parameters/:id} : delete the "id" floatParameter.
   *
   * @param id the id of the floatParameterDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/float-parameters/{id}")
  public Mono<ResponseEntity<Void>> deleteFloatParameter(@PathVariable UUID id) {
    log.debug("REST request to delete FloatParameter : {}", id);
    return floatParameterFacade
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
