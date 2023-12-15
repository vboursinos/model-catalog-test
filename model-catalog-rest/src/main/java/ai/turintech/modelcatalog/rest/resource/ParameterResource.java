package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.facade.ParameterFacade;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.service.ParameterServiceImpl;
import ai.turintech.modelcatalog.to.ParameterTO;
import ai.turintech.modelcatalog.todtomapper.ParameterMapper;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** REST controller for managing {@link Parameter}. */
@RestController
@RequestMapping("/api")
public class ParameterResource {

  private final Logger log = LoggerFactory.getLogger(ParameterResource.class);

  private static final String ENTITY_NAME = "modelCatalogParameter";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private ParameterServiceImpl parameterService;

  @Autowired private ParameterFacade parameterFacade;

  @Autowired private ParameterMapper parameterMapper;

  /**
   * {@code POST /parameters} : Create a new parameter.
   *
   * @param parameterTO the parameterTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     parameterTO, or with status {@code 400 (Bad Request)} if the parameter has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/parameters")
  public Mono<ResponseEntity<ParameterTO>> createParameter(
      @Valid @RequestBody ParameterTO parameterTO) {
    log.debug("REST request to save Parameter : {}", parameterTO);
    if (parameterTO.getId() != null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "A new parameter cannot already have an ID");
    }
    parameterTO.setId(UUID.randomUUID());
    return parameterFacade
        .save(parameterMapper.from(parameterTO))
        .map(parameterMapper::to)
        .map(
            result -> {
              try {
                return ResponseEntity.created(new URI("/api/parameters/" + result.getId()))
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
   * {@code PUT /parameters/:id} : Updates an existing parameter.
   *
   * @param id the id of the parameterTO to save.
   * @param parameterTO the parameterTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     parameterTO, or with status {@code 400 (Bad Request)} if the parameterTO is not valid, or
   *     with status {@code 500 (Internal Server Error)} if the parameterTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/parameters/{id}")
  public Mono<ResponseEntity<ParameterTO>> updateParameter(
      @PathVariable(value = "id", required = false) final UUID id,
      @Valid @RequestBody ParameterTO parameterTO) {
    log.debug("REST request to update Parameter : {}, {}", id, parameterTO);
    if (parameterTO.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot be null");
    }
    if (!Objects.equals(id, parameterTO.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
    }

    return parameterFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
              }

              return parameterFacade
                  .update(parameterMapper.from(parameterTO))
                  .map(parameterMapper::to)
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
   * {@code PATCH /parameters/:id} : Partial updates given fields of an existing parameter, field
   * will ignore if it is null
   *
   * @param id the id of the parameterTO to save.
   * @param parameterTO the parameterTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     parameterTO, or with status {@code 400 (Bad Request)} if the parameterTO is not valid, or
   *     with status {@code 404 (Not Found)} if the parameterTO is not found, or with status {@code
   *     500 (Internal Server Error)} if the parameterTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/parameters/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<ParameterTO>> partialUpdateParameter(
      @PathVariable(value = "id", required = false) final UUID id,
      @NotNull @RequestBody ParameterTO parameterTO) {
    log.debug("REST request to partial update Parameter partially : {}, {}", id, parameterTO);
    if (parameterTO.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be null");
    }
    if (!Objects.equals(id, parameterTO.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
    }

    return parameterFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
              }

              Mono<ParameterTO> result =
                  parameterFacade
                      .partialUpdate(parameterMapper.from(parameterTO))
                      .map(parameterMapper::to);

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
   * {@code GET /parameters} : get all the parameters.
   *
   * @param pageable the pagination information.
   * @param request a {@link ServerHttpRequest} request.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameters in
   *     body.
   */
  @GetMapping(value = "/parameters/non-stream", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<List<ParameterTO>>> getAllParameters(
      @org.springdoc.core.annotations.ParameterObject Pageable pageable,
      ServerHttpRequest request) {
    log.debug("REST request to get a page of Parameters");
    return parameterFacade
        .findAllPageable(pageable)
        .map(parameterMapper::toTO)
        .map(updatedListParameter -> ResponseEntity.ok().body(updatedListParameter))
        .defaultIfEmpty(ResponseEntity.notFound().build())
        .onErrorResume(
            Exception.class,
            ex -> {
              log.error("Error while fetching models: " + ex.getMessage(), ex);
              return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            });
  }

  /**
   * {@code GET /parameters/pageable-stream} : get all the parameters.
   *
   * @param pageable the pagination information.
   * @param request a {@link ServerHttpRequest} request.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameters in
   *     body.
   */
  @GetMapping(value = "/parameters", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<ParameterTO> getAllParametersAsStream(
      @org.springdoc.core.annotations.ParameterObject Pageable pageable,
      ServerHttpRequest request) {
    log.debug("REST request to get a page of Parameters");
    return parameterFacade.findPageableStream(pageable).map(parameterMapper::to);
  }

  /**
   * {@code GET /parameters/:id} : get the "id" parameter.
   *
   * @param id the id of the parameterTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parameterTO,
   *     or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/parameters/{id}")
  public Mono<ResponseEntity<ParameterTO>> getParameter(@PathVariable UUID id) {
    log.debug("REST request to get Parameter : {}", id);
    Mono<ParameterTO> parameterTO = parameterFacade.findOne(id).map(parameterMapper::to);
    return ResponseUtil.wrapOrNotFound(parameterTO);
  }

  /**
   * {@code DELETE /parameters/:id} : delete the "id" parameter.
   *
   * @param id the id of the parameterTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/parameters/{id}")
  public Mono<ResponseEntity<Void>> deleteParameter(@PathVariable UUID id) {
    log.debug("REST request to delete Parameter : {}", id);
    return parameterFacade
        .delete(id)
        .then(
            Mono.just(
                ResponseEntity.noContent()
                    .headers(
                        ai.turintech.modelcatalog.rest.support.HeaderUtil.createEntityDeletionAlert(
                            applicationName, true, ENTITY_NAME, id.toString()))
                    .build()));
  }
}
