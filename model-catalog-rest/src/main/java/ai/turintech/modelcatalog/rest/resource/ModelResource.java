package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.components.jpa.search.controller.AbstractPageableRestController;
import ai.turintech.components.jpa.search.data.to.PageLimitedTO;
import ai.turintech.components.jpa.search.data.to.PageableQueryRequestTO;
import ai.turintech.components.jpa.search.exception.PageableRequestException;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.exceptions.FindOneException;
import ai.turintech.modelcatalog.facade.ModelFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.service.ModelService;
import ai.turintech.modelcatalog.service.PaginationConverter;
import ai.turintech.modelcatalog.to.ModelPaginatedListTO;
import ai.turintech.modelcatalog.to.ModelTO;
import ai.turintech.modelcatalog.todtomapper.ModelMapper;
import ai.turintech.modelcatalog.todtomapper.ModelPaginatedListMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing {@link Model}. it extends AbstractPageableRestController to offer
 * generich search capabilities.
 */
@RestController
@RequestMapping("/api")
public class ModelResource extends AbstractPageableRestController<ModelTO, ModelDTO, ModelFacade> {

  public ModelResource() {
    super(ModelTO.class);
  }

  private final Logger log = LoggerFactory.getLogger(ModelResource.class);

  private static final String ENTITY_NAME = "Model";

  @Value("${spring.application.name}")
  private String applicationName;

  @Autowired private PaginationConverter paginationConverter;
  @Autowired private ModelService modelService;
  @Autowired private ModelFacade modelFacade;
  @Autowired private ModelMapper modelMapper;
  @Autowired private ModelPaginatedListMapper modelPaginatedListMapper;

  /**
   * {@code POST /models} : Create a new model.
   *
   * @param modelTO the modelTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     modelTO, or with status {@code 400 (Bad Request)} if the model has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/models")
  public Mono<ResponseEntity<ModelTO>> createModel(@Valid @RequestBody ModelTO modelTO)
      throws URISyntaxException {
    log.debug("REST request to save Model : {}", modelTO);
    if (modelTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new model cannot already have an ID", ENTITY_NAME, "idexists");
    }
    // modelTO.setId(UUID.randomUUID());
    return modelService
        .save(modelMapper.to(modelTO))
        .map(modelMapper::from)
        .map(
            result -> {
              try {
                return ResponseEntity.created(new URI("/api/models/" + result.getId()))
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
   * {@code PUT /models/:id} : Updates an existing model.
   *
   * @param id the id of the modelTO to save.
   * @param modelTO the modelTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     modelTO, or with status {@code 400 (Bad Request)} if the modelTO is not valid, or with
   *     status {@code 500 (Internal Server Error)} if the modelTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/models/{id}")
  public Mono<ResponseEntity<ModelTO>> updateModel(
      @PathVariable(value = "id", required = false) final UUID id,
      @Valid @RequestBody ModelTO modelTO)
      throws URISyntaxException {
    log.debug("REST request to update Model : {}, {}", id, modelTO);
    if (modelTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, modelTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return modelFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              return modelFacade
                  .update(modelMapper.to(modelTO))
                  .map(modelMapper::from)
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
   * {@code PATCH /models/:id} : Partial updates given fields of an existing model, field will
   * ignore if it is null
   *
   * @param id the id of the modelTO to save.
   * @param modelTO the modelTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     modelTO, or with status {@code 400 (Bad Request)} if the modelTO is not valid, or with
   *     status {@code 404 (Not Found)} if the modelTO is not found, or with status {@code 500
   *     (Internal Server Error)} if the modelTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
      value = "/models/{id}",
      consumes = {"application/json", "application/merge-patch+json"})
  public Mono<ResponseEntity<ModelTO>> partialUpdateModel(
      @PathVariable(value = "id", required = false) final UUID id,
      @NotNull @RequestBody ModelTO modelTO)
      throws URISyntaxException {
    log.debug("REST request to partial update Model partially : {}, {}", id, modelTO);
    if (modelTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, modelTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    return modelFacade
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
              }

              Mono<ModelTO> result =
                  modelFacade.partialUpdate(modelMapper.to(modelTO)).map(modelMapper::from);

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
   * {@code GET /models} : get all the models.
   *
   * @param pageable the pagination information.
   * @param eagerload flag to eager load entities from relationships (This is applicable for
   *     many-to-many).
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of models in body.
   */
  @GetMapping(value = "/models", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<ModelPaginatedListTO>> getAllModels(
      @ParameterObject Pageable pageable,
      ServerHttpRequest request,
      @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
    log.debug("REST request to get a page of Models");

    return modelFacade
        .findAll(pageable)
        .map(modelPaginatedListMapper::toTo)
        .map(modelPaginatedListTO -> ResponseEntity.ok().body(modelPaginatedListTO))
        .defaultIfEmpty(ResponseEntity.notFound().build())
        .onErrorResume(
            Exception.class,
            ex -> {
              log.error("Error while fetching models: " + ex.getMessage(), ex);
              return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            });
  }

  /**
   * Custom search endpoint for models.
   *
   * @param filter the pageable query request.
   * @return the ResponseEntity with status OK and the paged list of models in body.
   * @throws PageableRequestException if there is an issue with the pageable request.
   */
  @Override
  @GetMapping(value = "/models/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageLimitedTO<ModelTO>> findPagedByQuery(PageableQueryRequestTO filter)
      throws PageableRequestException {
    return super.findPagedByQuery(filter);
  }

  /**
   * {@code GET /models/:id} : get the "id" model.
   *
   * @param id the id of the modelTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelTO, or
   *     with status {@code 404 (Not Found)}.
   */
  @GetMapping("/models/{id}")
  public Mono<ResponseEntity<ModelTO>> getModel(@PathVariable UUID id) throws FindOneException {
    log.debug("REST request to get Model : {}", id);
    Mono<ModelTO> modelTO = modelFacade.findOne(id).map(modelMapper::to);
    return ResponseUtil.wrapOrNotFound(modelTO);
  }

  /**
   * {@code DELETE /models/:id} : delete the "id" model.
   *
   * @param id the id of the modelTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/models/{id}")
  public Mono<ResponseEntity<Void>> deleteModel(@PathVariable UUID id) {
    log.debug("REST request to delete Model : {}", id);
    return modelFacade
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
