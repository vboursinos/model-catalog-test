package ai.turintech.modelcatalog.rest.resource;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.facade.MetricFacade;
import ai.turintech.modelcatalog.repository.MetricRepository;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.service.MetricService;
import ai.turintech.modelcatalog.entity.Metric;
import ai.turintech.modelcatalog.to.MetricTO;
import ai.turintech.modelcatalog.todtomapper.MetricMapper;
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
 * REST controller for managing {@link Metric}.
 */
@RestController
@RequestMapping("/api")
public class MetricResource {

    private final Logger log = LoggerFactory.getLogger(MetricResource.class);

    private static final String ENTITY_NAME = "modelCatalogMetric";

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private MetricFacade metricFacade;

    @Autowired
    private MetricMapper metricMapper;

    /**
     * {@code POST  /metrics} : Create a new metric.
     *
     * @param metricDTO the metricDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metricDTO, or with status {@code 400 (Bad Request)} if the metric has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/metrics")
    public Mono<ResponseEntity<MetricTO>> createMetric(@RequestBody MetricTO metricTO) throws URISyntaxException {
        log.debug("REST request to save Metric : {}", metricTO);
        if (metricTO.getId() != null) {
            throw new BadRequestAlertException("A new metric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        metricTO.setId(UUID.randomUUID());
        return metricFacade
            .save(metricMapper.toDto(metricTO)).map(metricMapper::toTo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/metrics/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /metrics/:id} : Updates an existing metric.
     *
     * @param id the id of the metricDTO to save.
     * @param metricDTO the metricDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricDTO,
     * or with status {@code 400 (Bad Request)} if the metricDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metricDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/metrics/{id}")
    public Mono<ResponseEntity<MetricTO>> updateMetric(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody MetricTO metricTO
    ) throws URISyntaxException {
        log.debug("REST request to update Metric : {}, {}", id, metricTO);
        if (metricTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return metricFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return metricFacade
                    .update(metricMapper.toDto(metricTO)).map(metricMapper::toTo)
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
     * {@code PATCH  /metrics/:id} : Partial updates given fields of an existing metric, field will ignore if it is null
     *
     * @param id the id of the metricTO to save.
     * @param metricDTO the metricTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricTO,
     * or with status {@code 400 (Bad Request)} if the metricTO is not valid,
     * or with status {@code 404 (Not Found)} if the metricTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metricTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/metrics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<MetricTO>> partialUpdateMetric(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody MetricTO metricTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Metric partially : {}, {}", id, metricTO);
        if (metricTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return metricFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<MetricTO> result = metricFacade.partialUpdate(metricMapper.toDto(metricTO)).map(metricMapper::toTo);

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
     * {@code GET  /metrics} : get all the metrics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metrics in body.
     */
    @GetMapping(value = "/metrics", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<MetricTO>> getAllMetrics() {
        log.debug("REST request to get all Metrics");
        return metricFacade.findAll().collectList().map(metricMapper::toTo);
    }

    /**
     * {@code GET  /metrics} : get all the metrics as a stream.
     * @return the {@link Flux} of metrics.
     */
    @GetMapping(value = "/metrics", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MetricTO> getAllMetricsAsStream() {
        log.debug("REST request to get all Metrics as a stream");
        return metricFacade.findAll().map(metricMapper::toTo);
    }

    /**
     * {@code GET  /metrics/:id} : get the "id" metric.
     *
     * @param id the id of the metricTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metricTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/metrics/{id}")
    public Mono<ResponseEntity<MetricTO>> getMetric(@PathVariable UUID id) {
        log.debug("REST request to get Metric : {}", id);
        Mono<MetricTO> metricTO = metricFacade.findOne(id).map(metricMapper::toTo);
        return ResponseUtil.wrapOrNotFound(metricTO);
    }

    /**
     * {@code DELETE  /metrics/:id} : delete the "id" metric.
     *
     * @param id the id of the metricTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/metrics/{id}")
    public Mono<ResponseEntity<Void>> deleteMetric(@PathVariable UUID id) {
        log.debug("REST request to delete Metric : {}", id);
        return metricFacade
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
