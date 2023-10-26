package ai.turintech.modelcatalog.rest.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ai.turintech.modelcatalog.facade.ModelFamilyTypeFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.ModelFamilyTypeTO;
import ai.turintech.modelcatalog.todtomapper.ModelFamilyTypeMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing {@link ai.turintech.catalog.domain.ModelFamilyType}.
 */
@RestController
@RequestMapping("/api")
public class ModelFamilyTypeResource {

    private final Logger log = LoggerFactory.getLogger(ModelFamilyTypeResource.class);

    private static final String ENTITY_NAME = "modelCatalogModelFamilyType";

    @Value("${jhipster.clientApp.name:'modelCatalogApp'}")
    private String applicationName;

    private final ModelFamilyTypeFacade modelFamilyTypeFacade;
    
    private final ModelFamilyTypeMapper modelFamilyTypeMapper;


    public ModelFamilyTypeResource(ModelFamilyTypeFacade modelFamilyTypeFacade, ModelFamilyTypeMapper modelFamilyTypeMapper) {
        this.modelFamilyTypeFacade = modelFamilyTypeFacade;
        this.modelFamilyTypeMapper = modelFamilyTypeMapper;
    }

    /**
     * {@code POST  /model-family-types} : Create a new modelFamilyType.
     *
     * @param modelFamilyTypeDTO the modelFamilyTypeTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelFamilyTypeTO, or with status {@code 400 (Bad Request)} if the modelFamilyType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-family-types")
    public Mono<ResponseEntity<ModelFamilyTypeTO>> createModelFamilyType(@Valid @RequestBody ModelFamilyTypeTO modelFamilyTypeTO)
        throws URISyntaxException {
        log.debug("REST request to save ModelFamilyType : {}", modelFamilyTypeTO);
        if (modelFamilyTypeTO.getId() != null) {
            throw new BadRequestAlertException("A new modelFamilyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        modelFamilyTypeTO.setId(UUID.randomUUID());
        return modelFamilyTypeFacade
            .save(modelFamilyTypeMapper.toDto(modelFamilyTypeTO)).map(modelFamilyTypeMapper::toTo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/model-family-types/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /model-family-types/:id} : Updates an existing modelFamilyType.
     *
     * @param id the id of the modelFamilyTypeTO to save.
     * @param modelFamilyTypeDTO the modelFamilyTypeTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelFamilyTypeTO,
     * or with status {@code 400 (Bad Request)} if the modelFamilyTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelFamilyTypeTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-family-types/{id}")
    public Mono<ResponseEntity<ModelFamilyTypeTO>> updateModelFamilyType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ModelFamilyTypeTO modelFamilyTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModelFamilyType : {}, {}", id, modelFamilyTypeTO);
        if (modelFamilyTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelFamilyTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modelFamilyTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return modelFamilyTypeFacade
                    .update(modelFamilyTypeMapper.toDto(modelFamilyTypeTO)).map(modelFamilyTypeMapper::toTo)
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
     * {@code PATCH  /model-family-types/:id} : Partial updates given fields of an existing modelFamilyType, field will ignore if it is null
     *
     * @param id the id of the modelFamilyTypeTO to save.
     * @param modelFamilyTypeTO the modelFamilyTypeTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelFamilyTypeTO,
     * or with status {@code 400 (Bad Request)} if the modelFamilyTypeTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelFamilyTypeTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelFamilyTypeTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/model-family-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ModelFamilyTypeTO>> partialUpdateModelFamilyType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ModelFamilyTypeTO modelFamilyTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModelFamilyType partially : {}, {}", id, modelFamilyTypeTO);
        if (modelFamilyTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelFamilyTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return modelFamilyTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ModelFamilyTypeTO> result = modelFamilyTypeFacade.partialUpdate(modelFamilyTypeMapper.toDto(modelFamilyTypeTO)).map(modelFamilyTypeMapper::toTo);

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
     * {@code GET  /model-family-types} : get all the modelFamilyTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelFamilyTypes in body.
     */
    @GetMapping(value = "/model-family-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ModelFamilyTypeTO>> getAllModelFamilyTypes() {
        log.debug("REST request to get all ModelFamilyTypes");
        return modelFamilyTypeFacade.findAll().collectList().map(modelFamilyTypeMapper::toTo);
    }

    /**
     * {@code GET  /model-family-types} : get all the modelFamilyTypes as a stream.
     * @return the {@link Flux} of modelFamilyTypes.
     */
    @GetMapping(value = "/model-family-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ModelFamilyTypeTO> getAllModelFamilyTypesAsStream() {
        log.debug("REST request to get all ModelFamilyTypes as a stream");
        return modelFamilyTypeFacade.findAll().map(modelFamilyTypeMapper::toTo);
    }

    /**
     * {@code GET  /model-family-types/:id} : get the "id" modelFamilyType.
     *
     * @param id the id of the modelFamilyTypeTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelFamilyTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-family-types/{id}")
    public Mono<ResponseEntity<ModelFamilyTypeTO>> getModelFamilyType(@PathVariable UUID id) {
        log.debug("REST request to get ModelFamilyType : {}", id);
        Mono<ModelFamilyTypeTO> modelFamilyTypeTO = modelFamilyTypeFacade.findOne(id).map(modelFamilyTypeMapper::toTo);
        return ResponseUtil.wrapOrNotFound(modelFamilyTypeTO);
    }

    /**
     * {@code DELETE  /model-family-types/:id} : delete the "id" modelFamilyType.
     *
     * @param id the id of the modelFamilyTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-family-types/{id}")
    public Mono<ResponseEntity<Void>> deleteModelFamilyType(@PathVariable UUID id) {
        log.debug("REST request to delete ModelFamilyType : {}", id);
        return modelFamilyTypeFacade
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
