package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.dtoentitymapper.MetricMapper;
import ai.turintech.modelcatalog.entity.Metric;
import ai.turintech.modelcatalog.repository.MetricRepository;
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
        GenericCallable<MetricDTO, MetricDTO, Metric> callable = context.getBean(GenericCallable.class, "create", metricDTO, metricRepository, metricMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
        GenericCallable<MetricDTO, MetricDTO, Metric> callable = context.getBean(GenericCallable.class, "update", metricDTO, metricRepository, metricMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
        GenericCallable<MetricDTO, MetricDTO, Metric> callable = context.getBean(GenericCallable.class, "partialUpdate", metricDTO, metricRepository, metricMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Get all the metrics.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<MetricDTO>> findAll() {
        log.debug("Request to get all Metrics");
        GenericCallable<List<MetricDTO>, MetricDTO, Metric> callable = context.getBean(GenericCallable.class, "findAll", metricRepository, metricMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
        GenericCallable<MetricDTO, MetricDTO, Metric> callable = context.getBean(GenericCallable.class, "findById", id, metricRepository, metricMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the metric by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete Metric : {}", id);
        GenericCallable<Void, MetricDTO, Metric> callable = context.getBean(GenericCallable.class, "delete", id, metricRepository, metricMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        GenericCallable<Boolean, MetricDTO, Metric> callable = context.getBean(GenericCallable.class, "existsById", id, metricRepository, metricMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }
}
