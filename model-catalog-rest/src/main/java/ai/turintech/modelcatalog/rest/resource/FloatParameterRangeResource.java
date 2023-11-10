package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.facade.FloatParameterRangeFacade;
import ai.turintech.modelcatalog.repository.FloatParameterRangeRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.service.FloatParameterRangeService;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import ai.turintech.modelcatalog.to.FloatParameterRangeTO;
import ai.turintech.modelcatalog.todtomapper.FloatParameterRangeMapper;
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
 * REST controller for managing {@link FloatParameterRange}.
 */
@RestController
@RequestMapping("/api")
public class FloatParameterRangeResource {

    private final Logger log = LoggerFactory.getLogger(FloatParameterRangeResource.class);

    private static final String ENTITY_NAME = "modelCatalogFloatParameterRange";

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private FloatParameterRangeFacade floatParameterRangeFacade;
    @Autowired
    private FloatParameterRangeMapper floatParameterRangeMapper;

    /**
     * {@code POST  /float-parameter-ranges} : Create a new floatParameterRange.
     *
     * @param floatParameterRangeDTO the floatParameterRangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new floatParameterRangeDTO, or with status {@code 400 (Bad Request)} if the floatParameterRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/float-parameter-ranges")
    public Mono<ResponseEntity<FloatParameterRangeTO>> createFloatParameterRange(
        @Valid @RequestBody FloatParameterRangeTO floatParameterRangeTO
    ) throws URISyntaxException {
        log.debug("REST request to save FloatParameterRange : {}", floatParameterRangeTO);
        if (floatParameterRangeTO.getId() != null) {
            throw new BadRequestAlertException("A new floatParameterRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return floatParameterRangeFacade
            .save(floatParameterRangeMapper.toDto(floatParameterRangeTO)).map(floatParameterRangeMapper::toTo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/float-parameter-ranges/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /float-parameter-ranges/:id} : Updates an existing floatParameterRange.
     *
     * @param id the id of the floatParameterRangeDTO to save.
     * @param floatParameterRangeDTO the floatParameterRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floatParameterRangeDTO,
     * or with status {@code 400 (Bad Request)} if the floatParameterRangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the floatParameterRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/float-parameter-ranges/{id}")
    public Mono<ResponseEntity<FloatParameterRangeTO>> updateFloatParameterRange(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody FloatParameterRangeTO floatParameterRangeTO
    ) throws URISyntaxException {
        log.debug("REST request to update FloatParameterRange : {}, {}", id, floatParameterRangeTO);
        if (floatParameterRangeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floatParameterRangeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return floatParameterRangeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return floatParameterRangeFacade
                    .update(floatParameterRangeMapper.toDto(floatParameterRangeTO)).map(floatParameterRangeMapper::toTo)
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
     * {@code PATCH  /float-parameter-ranges/:id} : Partial updates given fields of an existing floatParameterRange, field will ignore if it is null
     *
     * @param id the id of the floatParameterRangeDTO to save.
     * @param floatParameterRangeDTO the floatParameterRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floatParameterRangeDTO,
     * or with status {@code 400 (Bad Request)} if the floatParameterRangeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the floatParameterRangeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the floatParameterRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/float-parameter-ranges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<FloatParameterRangeTO>> partialUpdateFloatParameterRange(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody FloatParameterRangeTO floatParameterRangeTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FloatParameterRange partially : {}, {}", id, floatParameterRangeTO);
        if (floatParameterRangeTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floatParameterRangeTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return floatParameterRangeFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<FloatParameterRangeTO> result = floatParameterRangeFacade.partialUpdate(floatParameterRangeMapper.toDto(floatParameterRangeTO)).map(floatParameterRangeMapper::toTo);

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
     * {@code GET  /float-parameter-ranges} : get all the floatParameterRanges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of floatParameterRanges in body.
     */
    @GetMapping(value = "/float-parameter-ranges", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<FloatParameterRangeTO>> getAllFloatParameterRanges() {
        log.debug("REST request to get all FloatParameterRanges");
        return floatParameterRangeFacade.findAll().collectList().map(floatParameterRangeMapper::toTo);
    }

    /**
     * {@code GET  /float-parameter-ranges} : get all the floatParameterRanges as a stream.
     * @return the {@link Flux} of floatParameterRanges.
     */
    @GetMapping(value = "/float-parameter-ranges", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<FloatParameterRangeTO> getAllFloatParameterRangesAsStream() {
        log.debug("REST request to get all FloatParameterRanges as a stream");
        return floatParameterRangeFacade.findAll().map(floatParameterRangeMapper::toTo);
    }

    /**
     * {@code GET  /float-parameter-ranges/:id} : get the "id" floatParameterRange.
     *
     * @param id the id of the floatParameterRangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the floatParameterRangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/float-parameter-ranges/{id}")
    public Mono<ResponseEntity<FloatParameterRangeTO>> getFloatParameterRange(@PathVariable UUID id) {
        log.debug("REST request to get FloatParameterRange : {}", id);
        Mono<FloatParameterRangeTO> floatParameterRangeTO = floatParameterRangeFacade.findOne(id).map(floatParameterRangeMapper::toTo);
        return ResponseUtil.wrapOrNotFound(floatParameterRangeTO);
    }

    /**
     * {@code DELETE  /float-parameter-ranges/:id} : delete the "id" floatParameterRange.
     *
     * @param id the id of the floatParameterRangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/float-parameter-ranges/{id}")
    public Mono<ResponseEntity<Void>> deleteFloatParameterRange(@PathVariable UUID id) {
        log.debug("REST request to delete FloatParameterRange : {}", id);
        return floatParameterRangeFacade
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
