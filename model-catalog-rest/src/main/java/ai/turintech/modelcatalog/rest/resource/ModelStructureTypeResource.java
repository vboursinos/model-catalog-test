package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.repository.ModelStructureTypeRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.service.ModelStructureTypeService;
import ai.turintech.modelcatalog.entity.ModelStructureType;
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
 * REST controller for managing {@link ModelStructureType}.
 */
@RestController
@RequestMapping("/api")
public class ModelStructureTypeResource {

    private final Logger log = LoggerFactory.getLogger(ModelStructureTypeResource.class);

    private static final String ENTITY_NAME = "modelCatalogModelStructureType";

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private ModelStructureTypeService modelStructureTypeService;

    @Autowired
    private ModelStructureTypeRepository modelStructureTypeRepository;

    /**
     * {@code POST  /model-structure-types} : Create a new modelStructureType.
     *
     * @param modelStructureTypeDTO the modelStructureTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelStructureTypeDTO, or with status {@code 400 (Bad Request)} if the modelStructureType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/model-structure-types")
    public Mono<ResponseEntity<ModelStructureTypeDTO>> createModelStructureType(
        @Valid @RequestBody ModelStructureTypeDTO modelStructureTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ModelStructureType : {}", modelStructureTypeDTO);
        if (modelStructureTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new modelStructureType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mono<ModelStructureTypeDTO> result = modelStructureTypeService.save(modelStructureTypeDTO);
        return result
            .map(
                res -> {
                    try {
                        return ResponseEntity
                            .created(new URI("/api/model-structure-types/" + res.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res);
                    } catch (URISyntaxException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                }
            );
    }

    /**
     * {@code PUT  /model-structure-types/:id} : Updates an existing modelStructureType.
     *
     * @param id the id of the modelStructureTypeDTO to save.
     * @param modelStructureTypeDTO the modelStructureTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelStructureTypeDTO,
     * or with status {@code 400 (Bad Request)} if the modelStructureTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelStructureTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/model-structure-types/{id}")
    public Mono<ResponseEntity<ModelStructureTypeDTO>> updateModelStructureType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ModelStructureTypeDTO modelStructureTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ModelStructureType : {}, {}", id, modelStructureTypeDTO);
        if (modelStructureTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelStructureTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelStructureTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mono<ModelStructureTypeDTO> result = modelStructureTypeService.update(modelStructureTypeDTO);
        return result
            .map(
                res -> ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                    .body(res)
            );
    }

    /**
     * {@code PATCH  /model-structure-types/:id} : Partial updates given fields of an existing modelStructureType, field will ignore if it is null
     *
     * @param id the id of the modelStructureTypeDTO to save.
     * @param modelStructureTypeDTO the modelStructureTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelStructureTypeDTO,
     * or with status {@code 400 (Bad Request)} if the modelStructureTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelStructureTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelStructureTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/model-structure-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ModelStructureTypeDTO>> partialUpdateModelStructureType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ModelStructureTypeDTO modelStructureTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModelStructureType partially : {}, {}", id, modelStructureTypeDTO);
        if (modelStructureTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelStructureTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelStructureTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mono<ModelStructureTypeDTO> result = modelStructureTypeService.partialUpdate(modelStructureTypeDTO);

        return result
            .map(
                res -> ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                    .body(res)
            );
    }

    /**
     * {@code GET  /model-structure-types} : get all the modelStructureTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelStructureTypes in body.
     */
    @GetMapping(value = "/model-structure-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<ModelStructureTypeDTO>>> getAllModelStructureTypes() {
        log.debug("REST request to get all ModelStructureTypes");
        return modelStructureTypeService.findAll().map(list -> ResponseEntity.ok().body(list));
    }

    /**
     * {@code GET  /model-structure-types} : get all the modelStructureTypes as a stream.
     * @return the {@link Flux} of modelStructureTypes.
     */
    @GetMapping(value = "/model-structure-types/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ModelStructureTypeDTO> getAllModelStructureTypesAsStream() {
        log.debug("REST request to get all ModelStructureTypes as a stream");
        return modelStructureTypeService.findAllStream();
    }

    /**
     * {@code GET  /model-structure-types/:id} : get the "id" modelStructureType.
     *
     * @param id the id of the modelStructureTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelStructureTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/model-structure-types/{id}")
    public Mono<ResponseEntity<ModelStructureTypeDTO>> getModelStructureType(@PathVariable UUID id) {
        log.debug("REST request to get ModelStructureType : {}", id);
        Mono<ModelStructureTypeDTO> modelStructureTypeDTO = modelStructureTypeService.findOne(id);
        return modelStructureTypeDTO.map(
                modelStructure -> ResponseEntity.ok().body(modelStructure)
        );
    }

    /**
     * {@code DELETE  /model-structure-types/:id} : delete the "id" modelStructureType.
     *
     * @param id the id of the modelStructureTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/model-structure-types/{id}")
    public Mono<ResponseEntity<Void>> deleteModelStructureType(@PathVariable UUID id) {
        log.debug("REST request to delete ModelStructureType : {}", id);
        modelStructureTypeService.delete(id);
        return Mono.justOrEmpty(ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build());
    }
}
