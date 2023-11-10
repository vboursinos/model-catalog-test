package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.FloatParameterRangeCallable;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterRangeMapper;
import ai.turintech.modelcatalog.repository.FloatParameterRangeRepository;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
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
 * Service Implementation for managing {@link FloatParameterRange}.
 */
@Service
@Transactional
public class FloatParameterRangeService {

    private final Logger log = LoggerFactory.getLogger(FloatParameterRangeService.class);
    @Autowired
    private ApplicationContext context;
    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private FloatParameterRangeRepository floatParameterRangeRepository;
    @Autowired
    private FloatParameterRangeMapper floatParameterRangeMapper;

    /**
     * Save a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<FloatParameterRangeDTO> save(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to save FloatParameterRange : {}", floatParameterRangeDTO);
        Callable<FloatParameterRangeDTO> callable = context.getBean(FloatParameterRangeCallable.class, "create", floatParameterRangeDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<FloatParameterRangeDTO> update(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to update FloatParameterRange : {}", floatParameterRangeDTO);
        Callable<FloatParameterRangeDTO> callable = context.getBean(FloatParameterRangeCallable.class, "update", floatParameterRangeDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a floatParameterRange.
     *
     * @param floatParameterRangeDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<FloatParameterRangeDTO> partialUpdate(FloatParameterRangeDTO floatParameterRangeDTO) {
        log.debug("Request to partially update FloatParameterRange : {}", floatParameterRangeDTO);
        Callable<FloatParameterRangeDTO> callable = context.getBean(FloatParameterRangeCallable.class, "partialUpdate", floatParameterRangeDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the floatParameterRanges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<FloatParameterRangeDTO>> findAll() {
        log.debug("Request to get all FloatParameterRanges");
        Callable<List<FloatParameterRangeDTO>> callable = context.getBean(FloatParameterRangeCallable.class, "findAll");
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<FloatParameterRangeDTO> findAllStream() {
        log.debug("Request to get all FloatParameterRanges");

        return Flux.defer(() -> Flux.fromStream(
                        floatParameterRangeRepository.findAll().stream()
                                .map(floatParameterRangeMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }
    /**
     * Get one floatParameterRange by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<FloatParameterRangeDTO> findOne(UUID id) {
        log.debug("Request to get FloatParameterRange : {}", id);
        Callable<FloatParameterRangeDTO> callable = context.getBean(FloatParameterRangeCallable.class, "findById", id);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Delete the floatParameterRange by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete FloatParameterRange : {}", id);
        Callable<Void> callable = context.getBean(FloatParameterRangeCallable.class, "delete", id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        return Mono.just(floatParameterRangeRepository.existsById(id));
    }
}
