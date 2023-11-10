package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.facade.ModelTypeFacade;
import ai.turintech.modelcatalog.repository.ModelTypeRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.service.ModelTypeService;
import ai.turintech.modelcatalog.to.ModelTypeTO;
import ai.turintech.modelcatalog.entity.ModelType;
import ai.turintech.modelcatalog.todtomapper.ModelTypeMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * REST controller for managing {@link ModelType}.
 */
@RestController
@RequestMapping("/api")
public class ModelTypeResource {

    private final Logger log = LoggerFactory.getLogger(ModelTypeResource.class);

    private static final String ENTITY_NAME = "modelCatalogModelType";

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private ModelTypeMapper modelTypeMapper;

    @Autowired
    private ModelTypeFacade modelTypeFacade;

    /**
     * {@code POST  /model-types} : Create a new modelType.
     *
     * @param ModelTypeTO the ModelTypeTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ModelTypeTO, or with status {@code 400 (Bad Request)} if the modelType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-types")
    public Mono<ResponseEntity<ModelTypeTO>> createModelType(@Valid @RequestBody ModelTypeTO modelTypeTO) throws URISyntaxException {
        log.debug("REST request to save ModelType : {}", modelTypeTO);
        if (modelTypeTO.getId() != null) {
            throw new BadRequestAlertException("A new modelType cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        modelTypeTO.setId(UUID.randomUUID());
        return modelTypeFacade
            .save(modelTypeMapper.toDto(modelTypeTO)).map(modelTypeMapper::toTo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/model-types/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /model-types/:id} : Updates an existing modelType.
     *
     * @param id the id of the ModelTypeTO to save.
     * @param ModelTypeTO the ModelTypeTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ModelTypeTO,
     * or with status {@code 400 (Bad Request)} if the ModelTypeTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ModelTypeTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-types/{id}")
    public Mono<ResponseEntity<ModelTypeTO>> updateModelType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ModelTypeTO modelTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModelType : {}, {}", id, modelTypeTO);
        if (modelTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modelTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return modelTypeFacade
                    .update(modelTypeMapper.toDto(modelTypeTO)).map(modelTypeMapper::toTo)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /model-types/:id} : Partial updates given fields of an existing modelType, field will ignore if it is null
     *
     * @param id the id of the ModelTypeTO to save.
     * @param ModelTypeTO the ModelTypeTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ModelTypeTO,
     * or with status {@code 400 (Bad Request)} if the ModelTypeTO is not valid,
     * or with status {@code 404 (Not Found)} if the ModelTypeTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ModelTypeTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/model-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ModelTypeTO>> partialUpdateModelType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ModelTypeTO modelTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModelType partially : {}, {}", id, modelTypeTO);
        if (modelTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modelTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ModelTypeTO> result = modelTypeFacade.partialUpdate(modelTypeMapper.toDto(modelTypeTO)).map(modelTypeMapper::toTo);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /model-types} : get all the modelTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelTypes in body.
     */
    @GetMapping(value = "/model-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ModelTypeTO>> getAllModelTypes() {
        log.debug("REST request to get all ModelTypes");
        return modelTypeFacade.findAll().collectList().map(modelTypeMapper::toTo);
    }

    /**
     * {@code GET  /model-types} : get all the modelTypes as a stream.
     * @return the {@link Flux} of modelTypes.
     */
    @GetMapping(value = "/model-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ModelTypeTO> getAllModelTypesAsStream() {
        log.debug("REST request to get all ModelTypes as a stream");
        return modelTypeFacade.findAll().map(modelTypeMapper::toTo);
    }

    /**
     * {@code GET  /model-types/:id} : get the "id" modelType.
     *
     * @param id the id of the ModelTypeTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ModelTypeTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-types/{id}")
    public Mono<ResponseEntity<ModelTypeTO>> getModelType(@PathVariable UUID id) {
        log.debug("REST request to get ModelType : {}", id);
        Mono<ModelTypeTO> modelTypeTO = modelTypeFacade.findOne(id).map(modelTypeMapper::toTo);
        return ResponseUtil.wrapOrNotFound(modelTypeTO);
    }

    /**
     * {@code DELETE  /model-types/:id} : delete the "id" modelType.
     *
     * @param id the id of the ModelTypeTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-types/{id}")
    public Mono<ResponseEntity<Void>> deleteModelType(@PathVariable UUID id) {
        log.debug("REST request to delete ModelType : {}", id);
        return modelTypeFacade
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
