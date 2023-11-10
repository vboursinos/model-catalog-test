package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.facade.ParameterDistributionTypeFacade;
import ai.turintech.modelcatalog.repository.ParameterDistributionTypeRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.service.ParameterDistributionTypeService;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.to.ParameterDistributionTypeTO;
import ai.turintech.modelcatalog.todtomapper.ParameterDistributionTypeMapper;
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
 * REST controller for managing {@link ParameterDistributionType}.
 */
@RestController
@RequestMapping("/api")
public class ParameterDistributionTypeResource {

    private final Logger log = LoggerFactory.getLogger(ParameterDistributionTypeResource.class);

    private static final String ENTITY_NAME = "modelCatalogParameterDistributionType";

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private ParameterDistributionTypeFacade parameterDistributionTypeFacade;

    @Autowired
    private ParameterDistributionTypeMapper parameterDistributionTypeMapper;

    /**
     * {@code POST  /parameter-distribution-types} : Create a new parameterDistributionType.
     *
     * @param ParameterDistributionTypeTO the ParameterDistributionTypeTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ParameterDistributionTypeTO, or with status {@code 400 (Bad Request)} if the parameterDistributionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameter-distribution-types")
    public Mono<ResponseEntity<ParameterDistributionTypeTO>> createParameterDistributionType(
        @Valid @RequestBody ParameterDistributionTypeTO parameterDistributionTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to save ParameterDistributionType : {}", parameterDistributionTypeTO);
        if (parameterDistributionTypeTO.getId() != null) {
            throw new BadRequestAlertException("A new parameterDistributionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parameterDistributionTypeTO.setId(UUID.randomUUID());
        return parameterDistributionTypeFacade
            .save(parameterDistributionTypeMapper.toDto(parameterDistributionTypeTO)).map(parameterDistributionTypeMapper::toTo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/parameter-distribution-types/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /parameter-distribution-types/:id} : Updates an existing parameterDistributionType.
     *
     * @param id the id of the ParameterDistributionTypeTO to save.
     * @param ParameterDistributionTypeTO the ParameterDistributionTypeTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ParameterDistributionTypeTO,
     * or with status {@code 400 (Bad Request)} if the ParameterDistributionTypeTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ParameterDistributionTypeTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameter-distribution-types/{id}")
    public Mono<ResponseEntity<ParameterDistributionTypeTO>> updateParameterDistributionType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ParameterDistributionTypeTO parameterDistributionTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParameterDistributionType : {}, {}", id, parameterDistributionTypeTO);
        if (parameterDistributionTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameterDistributionTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return parameterDistributionTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return parameterDistributionTypeFacade
                    .update(parameterDistributionTypeMapper.toDto(parameterDistributionTypeTO)).map(parameterDistributionTypeMapper::toTo)
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
     * {@code PATCH  /parameter-distribution-types/:id} : Partial updates given fields of an existing parameterDistributionType, field will ignore if it is null
     *
     * @param id the id of the ParameterDistributionTypeTO to save.
     * @param ParameterDistributionTypeTO the ParameterDistributionTypeTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ParameterDistributionTypeTO,
     * or with status {@code 400 (Bad Request)} if the ParameterDistributionTypeTO is not valid,
     * or with status {@code 404 (Not Found)} if the ParameterDistributionTypeTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ParameterDistributionTypeTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parameter-distribution-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ParameterDistributionTypeTO>> partialUpdateParameterDistributionType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ParameterDistributionTypeTO parameterDistributionTypeTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParameterDistributionType partially : {}, {}", id, parameterDistributionTypeTO);
        if (parameterDistributionTypeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameterDistributionTypeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return parameterDistributionTypeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ParameterDistributionTypeTO> result = parameterDistributionTypeFacade.partialUpdate(parameterDistributionTypeMapper.toDto(parameterDistributionTypeTO)).map(parameterDistributionTypeMapper::toTo);

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
     * {@code GET  /parameter-distribution-types} : get all the parameterDistributionTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameterDistributionTypes in body.
     */
    @GetMapping(value = "/parameter-distribution-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ParameterDistributionTypeTO>> getAllParameterDistributionTypes() {
        log.debug("REST request to get all ParameterDistributionTypes");
        return parameterDistributionTypeFacade.findAll().collectList().map(parameterDistributionTypeMapper::toTo);
    }

    /**
     * {@code GET  /parameter-distribution-types} : get all the parameterDistributionTypes as a stream.
     * @return the {@link Flux} of parameterDistributionTypes.
     */
    @GetMapping(value = "/parameter-distribution-types", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ParameterDistributionTypeTO> getAllParameterDistributionTypesAsStream() {
        log.debug("REST request to get all ParameterDistributionTypes as a stream");
        return parameterDistributionTypeFacade.findAll().map(parameterDistributionTypeMapper::toTo);
    }

    /**
     * {@code GET  /parameter-distribution-types/:id} : get the "id" parameterDistributionType.
     *
     * @param id the id of the ParameterDistributionTypeTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ParameterDistributionTypeTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameter-distribution-types/{id}")
    public Mono<ResponseEntity<ParameterDistributionTypeTO>> getParameterDistributionType(@PathVariable UUID id) {
        log.debug("REST request to get ParameterDistributionType : {}", id);
        Mono<ParameterDistributionTypeTO> parameterDistributionTypeTO = parameterDistributionTypeFacade.findOne(id).map(parameterDistributionTypeMapper::toTo);
        return ResponseUtil.wrapOrNotFound(parameterDistributionTypeTO);
    }

    /**
     * {@code DELETE  /parameter-distribution-types/:id} : delete the "id" parameterDistributionType.
     *
     * @param id the id of the ParameterDistributionTypeTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameter-distribution-types/{id}")
    public Mono<ResponseEntity<Void>> deleteParameterDistributionType(@PathVariable UUID id) {
        log.debug("REST request to delete ParameterDistributionType : {}", id);
        return parameterDistributionTypeFacade
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
