package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.repository.ParameterTypeRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.service.ParameterTypeService;
import ai.turintech.modelcatalog.entity.ParameterType;
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
 * REST controller for managing {@link ParameterType}.
 */
@RestController
@RequestMapping("/api")
public class ParameterTypeResource {

    private final Logger log = LoggerFactory.getLogger(ParameterTypeResource.class);

    private static final String ENTITY_NAME = "modelCatalogParameterType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private ParameterTypeService parameterTypeService;

    @Autowired
    private ParameterTypeRepository parameterTypeRepository;

    /**
     * {@code POST  /parameter-types} : Create a new parameterType.
     *
     * @param parameterTypeDTO the parameterTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parameterTypeDTO, or with status {@code 400 (Bad Request)} if the parameterType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameter-types")
    public Mono<ResponseEntity<ParameterTypeDTO>> createParameterType(@Valid @RequestBody ParameterTypeDTO parameterTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ParameterType : {}", parameterTypeDTO);
        if (parameterTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new parameterType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mono<ParameterTypeDTO> result = parameterTypeService.save(parameterTypeDTO);
        return result
            .map(
                (res) -> {
                    try {
                        return ResponseEntity
                            .created(new URI("/api/parameter-types/" + res.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res);
                    } catch (URISyntaxException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                }
            );
    }

    /**
     * {@code PUT  /parameter-types/:id} : Updates an existing parameterType.
     *
     * @param id the id of the parameterTypeDTO to save.
     * @param parameterTypeDTO the parameterTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parameterTypeDTO,
     * or with status {@code 400 (Bad Request)} if the parameterTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parameterTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameter-types/{id}")
    public Mono<ResponseEntity<ParameterTypeDTO>> updateParameterType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ParameterTypeDTO parameterTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParameterType : {}, {}", id, parameterTypeDTO);
        if (parameterTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameterTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parameterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mono<ParameterTypeDTO> result = parameterTypeService.update(parameterTypeDTO);
        return result
            .map(
                (res) -> ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                    .body(res)
            );
    }

    /**
     * {@code PATCH  /parameter-types/:id} : Partial updates given fields of an existing parameterType, field will ignore if it is null
     *
     * @param id the id of the parameterTypeDTO to save.
     * @param parameterTypeDTO the parameterTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parameterTypeDTO,
     * or with status {@code 400 (Bad Request)} if the parameterTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parameterTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parameterTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parameter-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ParameterTypeDTO>> partialUpdateParameterType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ParameterTypeDTO parameterTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParameterType partially : {}, {}", id, parameterTypeDTO);
        if (parameterTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameterTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parameterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mono<ParameterTypeDTO> result = parameterTypeService.partialUpdate(parameterTypeDTO);

        return result
            .map(
                (res) -> ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                    .body(res)
            );
    }

    /**
     * {@code GET  /parameter-types} : get all the parameterTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameterTypes in body.
     */
    @GetMapping(value = "/parameter-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<ParameterTypeDTO>>> getAllParameterTypes() {
        log.debug("REST request to get all ParameterTypes");
        return parameterTypeService.findAll().map((res) -> new ResponseEntity<>(res, HttpStatus.OK));
    }

    /**
     * {@code GET  /parameter-types} : get all the parameterTypes as a stream.
     * @return the {@link Flux} of parameterTypes.
     */
    @GetMapping(value = "/parameter-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ParameterTypeDTO> getAllParameterTypesAsStream() {
        log.debug("REST request to get all ParameterTypes as a stream");
        return parameterTypeService.findAllStream();
    }

    /**
     * {@code GET  /parameter-types/:id} : get the "id" parameterType.
     *
     * @param id the id of the parameterTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parameterTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameter-types/{id}")
    public Mono<ResponseEntity<ParameterTypeDTO>> getParameterType(@PathVariable UUID id) {
        log.debug("REST request to get ParameterType : {}", id);
        Mono<ParameterTypeDTO> parameterTypeDTO = parameterTypeService.findOne(id);
        return parameterTypeDTO.map(
                parameterType -> ResponseEntity.ok().body(parameterType)
        );
    }
    /**
     * {@code DELETE  /parameter-types/:id} : delete the "id" parameterType.
     *
     * @param id the id of the parameterTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameter-types/{id}")
    public Mono<ResponseEntity<Void>> deleteParameterType(@PathVariable UUID id) {
        log.debug("REST request to delete ParameterType : {}", id);
        parameterTypeService.delete(id);
        return Mono.just(ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build());
    }
}
