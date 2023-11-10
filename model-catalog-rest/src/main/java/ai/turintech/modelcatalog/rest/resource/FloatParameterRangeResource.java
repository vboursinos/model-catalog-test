package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.repository.FloatParameterRangeRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.service.FloatParameterRangeService;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private FloatParameterRangeService floatParameterRangeService;
    @Autowired
    private FloatParameterRangeRepository floatParameterRangeRepository;

    /**
     * {@code POST  /float-parameter-ranges} : Create a new floatParameterRange.
     *
     * @param floatParameterRangeDTO the floatParameterRangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new floatParameterRangeDTO, or with status {@code 400 (Bad Request)} if the floatParameterRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/float-parameter-ranges")
    public Mono<ResponseEntity<FloatParameterRangeDTO>> createFloatParameterRange(
            @Valid @RequestBody FloatParameterRangeDTO floatParameterRangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FloatParameterRange : {}", floatParameterRangeDTO);
        if (floatParameterRangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new floatParameterRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mono<FloatParameterRangeDTO> result = floatParameterRangeService.save(floatParameterRangeDTO);
        return result.map((FloatParameterRangeDTO dto) -> ResponseEntity.created(URI.create("/api/float-parameter-ranges/" + dto.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
                .body(dto));
    }

    /**
     * {@code PUT  /float-parameter-ranges/:id} : Updates an existing floatParameterRange.
     *
     * @param id                     the id of the floatParameterRangeDTO to save.
     * @param floatParameterRangeDTO the floatParameterRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floatParameterRangeDTO,
     * or with status {@code 400 (Bad Request)} if the floatParameterRangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the floatParameterRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/float-parameter-ranges/{id}")
    public Mono<ResponseEntity<FloatParameterRangeDTO>> updateFloatParameterRange(
            @PathVariable(value = "id", required = false) final UUID id,
            @Valid @RequestBody FloatParameterRangeDTO floatParameterRangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FloatParameterRange : {}, {}", id, floatParameterRangeDTO);
        if (floatParameterRangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floatParameterRangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floatParameterRangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mono<FloatParameterRangeDTO> result = floatParameterRangeService.update(floatParameterRangeDTO);
        return result.map((FloatParameterRangeDTO dto) -> ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
                .body(dto));
    }

    /**
     * {@code PATCH  /float-parameter-ranges/:id} : Partial updates given fields of an existing floatParameterRange, field will ignore if it is null
     *
     * @param id                     the id of the floatParameterRangeDTO to save.
     * @param floatParameterRangeDTO the floatParameterRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floatParameterRangeDTO,
     * or with status {@code 400 (Bad Request)} if the floatParameterRangeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the floatParameterRangeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the floatParameterRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/float-parameter-ranges/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public Mono<ResponseEntity<FloatParameterRangeDTO>> partialUpdateFloatParameterRange(
            @PathVariable(value = "id", required = false) final UUID id,
            @NotNull @RequestBody FloatParameterRangeDTO floatParameterRangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FloatParameterRange partially : {}, {}", id, floatParameterRangeDTO);
        if (floatParameterRangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floatParameterRangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floatParameterRangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mono<FloatParameterRangeDTO> result = floatParameterRangeService.partialUpdate(floatParameterRangeDTO);

        return result.map((FloatParameterRangeDTO dto) -> ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
                .body(dto));
    }

    /**
     * {@code GET  /float-parameter-ranges} : get all the floatParameterRanges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of floatParameterRanges in body.
     */
    @GetMapping(value = "/float-parameter-ranges", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<FloatParameterRangeDTO>>> getAllFloatParameterRanges() {
        log.debug("REST request to get all FloatParameterRanges");
        return floatParameterRangeService.findAll().map((List<FloatParameterRangeDTO> floatParameterRangeDTOS) ->
                ResponseEntity.ok().body(floatParameterRangeDTOS));
    }

    /**
     * {@code GET  /float-parameter-ranges} : get all the floatParameterRanges as a stream.
     *
     * @return the {@link Flux} of floatParameterRanges.
     */
    @GetMapping(value = "/float-parameter-ranges/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<FloatParameterRangeDTO> getAllFloatParameterRangesAsStream() {
        log.debug("REST request to get all FloatParameterRanges as a stream");
        return floatParameterRangeService.findAllStream();
    }

    /**
     * {@code GET  /float-parameter-ranges/:id} : get the "id" floatParameterRange.
     *
     * @param id the id of the floatParameterRangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the floatParameterRangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/float-parameter-ranges/{id}")
    public Mono<ResponseEntity<FloatParameterRangeDTO>> getFloatParameterRange(@PathVariable UUID id) {
        log.debug("REST request to get FloatParameterRange : {}", id);
        Mono<FloatParameterRangeDTO> floatParameterRangeDTO = floatParameterRangeService.findOne(id);
        return floatParameterRangeDTO
                .map((response) -> ResponseEntity.ok().body(response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
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
        floatParameterRangeService.delete(id);
        return Mono.justOrEmpty(ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build());
    }
}
