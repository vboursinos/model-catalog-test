package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import ai.turintech.modelcatalog.facade.ModelEnsembleTypeFacade;
import ai.turintech.modelcatalog.repository.ModelEnsembleTypeRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.service.ModelEnsembleTypeService;
import ai.turintech.modelcatalog.to.ModelEnsembleTypeTO;
import ai.turintech.modelcatalog.todtomapper.ModelEnsembleTypeMapper;
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
 * REST controller for managing {@link ModelEnsembleType}.
 */
@RestController
@RequestMapping("/api")
public class ModelEnsembleTypeResource {

    private final Logger log = LoggerFactory.getLogger(ModelEnsembleTypeResource.class);

    private static final String ENTITY_NAME = "modelCatalogModelEnsembleType";

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private ModelEnsembleTypeFacade modelEnsembleTypeFacade;

    @Autowired
    private ModelEnsembleTypeMapper modelEnsembleTypeMapper;

    /**
     * {@code POST  /model-ensemble-types} : Create a new modelEnsembleType.
     *
     * @param modelEnsembleTypeTO the modelEnsembleTypeTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelEnsembleTypeTO, or with status {@code 400 (Bad Request)} if the modelEnsembleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-ensemble-types")
    public Mono<ResponseEntity<ModelEnsembleTypeTO>> createModelEnsembleType(
        @Valid @RequestBody ModelEnsembleTypeTO modelEnsembleTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to save ModelEnsembleType : {}", modelEnsembleTypeTO);
        if (modelEnsembleTypeTO.getId() != null) {
            throw new BadRequestAlertException("A new modelEnsembleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        modelEnsembleTypeTO.setId(UUID.randomUUID());
        return modelEnsembleTypeFacade
            .save(modelEnsembleTypeMapper.toDto(modelEnsembleTypeTO)).map(modelEnsembleTypeMapper::toTo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/model-ensemble-types/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /model-ensemble-types/:id} : Updates an existing modelEnsembleType.
     *
     * @param id the id of the modelEnsembleTypeTO to save.
     * @param modelEnsembleTypeTO the modelEnsembleTypeTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelEnsembleTypeTO,
     * or with status {@code 400 (Bad Request)} if the modelEnsembleTypeTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelEnsembleTypeTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-ensemble-types/{id}")
    public Mono<ResponseEntity<ModelEnsembleTypeTO>> updateModelEnsembleType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ModelEnsembleTypeTO modelEnsembleTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModelEnsembleType : {}, {}", id, modelEnsembleTypeTO);
        if (modelEnsembleTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelEnsembleTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modelEnsembleTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return modelEnsembleTypeFacade
                    .update(modelEnsembleTypeMapper.toDto(modelEnsembleTypeTO)).map(modelEnsembleTypeMapper::toTo)
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
     * {@code PATCH  /model-ensemble-types/:id} : Partial updates given fields of an existing modelEnsembleType, field will ignore if it is null
     *
     * @param id the id of the modelEnsembleTypeDTO to save.
     * @param modelEnsembleTypeTO the modelEnsembleTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelEnsembleTypeDTO,
     * or with status {@code 400 (Bad Request)} if the modelEnsembleTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelEnsembleTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelEnsembleTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/model-ensemble-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ModelEnsembleTypeTO>> partialUpdateModelEnsembleType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ModelEnsembleTypeTO modelEnsembleTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModelEnsembleType partially : {}, {}", id, modelEnsembleTypeTO);
        if (modelEnsembleTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelEnsembleTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modelEnsembleTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ModelEnsembleTypeTO> result = modelEnsembleTypeFacade.partialUpdate(modelEnsembleTypeMapper.toDto(modelEnsembleTypeTO)).map(modelEnsembleTypeMapper::toTo);

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
     * {@code GET  /model-ensemble-types} : get all the modelEnsembleTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelEnsembleTypes in body.
     */
    @GetMapping(value = "/model-ensemble-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ModelEnsembleTypeTO>> getAllModelEnsembleTypes() {
        log.debug("REST request to get all ModelEnsembleTypes");
        return modelEnsembleTypeFacade.findAll().collectList().map(modelEnsembleTypeMapper::toTo);
    }

    /**
     * {@code GET  /model-ensemble-types} : get all the modelEnsembleTypes as a stream.
     * @return the {@link Flux} of modelEnsembleTypes.
     */
    @GetMapping(value = "/model-ensemble-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ModelEnsembleTypeTO> getAllModelEnsembleTypesAsStream() {
        log.debug("REST request to get all ModelEnsembleTypes as a stream");
        return modelEnsembleTypeFacade.findAll().map(modelEnsembleTypeMapper::toTo);
    }

    /**
     * {@code GET  /model-ensemble-types/:id} : get the "id" modelEnsembleType.
     *
     * @param id the id of the modelEnsembleTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelEnsembleTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-ensemble-types/{id}")
    public Mono<ResponseEntity<ModelEnsembleTypeTO>> getModelEnsembleType(@PathVariable UUID id) {
        log.debug("REST request to get ModelEnsembleType : {}", id);
        Mono<ModelEnsembleTypeTO> modelEnsembleTypeTO = modelEnsembleTypeFacade.findOne(id).map(modelEnsembleTypeMapper::toTo);
        return ResponseUtil.wrapOrNotFound(modelEnsembleTypeTO);
    }

    /**
     * {@code DELETE  /model-ensemble-types/:id} : delete the "id" modelEnsembleType.
     *
     * @param id the id of the modelEnsembleTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-ensemble-types/{id}")
    public Mono<ResponseEntity<Void>> deleteModelEnsembleType(@PathVariable UUID id) {
        log.debug("REST request to delete ModelEnsembleType : {}", id);
        return modelEnsembleTypeFacade
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
