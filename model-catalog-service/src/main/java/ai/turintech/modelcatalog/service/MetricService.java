package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dtoentitymapper.MetricMapper;
import ai.turintech.modelcatalog.repository.MetricRepository;
import ai.turintech.modelcatalog.callable.MetricCallable;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Service Implementation for managing {@link Metric}.
 */
@Service
@Transactional
public class MetricService {

    private final Logger log = LoggerFactory.getLogger(MetricService.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private MetricMapper metricMapper;


    /**
     * Save a metric.
     *
     * @param metricDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<MetricDTO> save(MetricDTO metricDTO) {
        log.debug("Request to save Metric : {}", metricDTO);
        Callable<MetricDTO> callable = context.getBean(MetricCallable.class, "create", metricDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a metric.
     *
     * @param metricDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<MetricDTO> update(MetricDTO metricDTO) {
        log.debug("Request to update Metric : {}", metricDTO);
        Callable<MetricDTO> callable = context.getBean(MetricCallable.class, "update", metricDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a metric.
     *
     * @param metricDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<MetricDTO> partialUpdate(MetricDTO metricDTO) {
        log.debug("Request to partially update Metric : {}", metricDTO);
        Callable<MetricDTO> callable = context.getBean(MetricCallable.class, "partialUpdate", metricDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the metrics.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<MetricDTO>> findAll() {
        log.debug("Request to get all Metrics");
        Callable<List<MetricDTO>> callable = context.getBean(MetricCallable.class, "findAll");
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<MetricDTO> findAllStream() {
        log.debug("Request to get all Metrics");

        return Flux.defer(() -> Flux.fromStream(
                        metricRepository.findAll().stream()
                                .map(metricMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get one metric by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<MetricDTO> findOne(UUID id) {
        log.debug("Request to get Metric : {}", id);
        Callable<MetricDTO> callable = context.getBean(MetricCallable.class, "findById", id);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Delete the metric by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete Metric : {}", id);
        Callable<MetricDTO> callable = context.getBean(MetricCallable.class, "delete", id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }
}
