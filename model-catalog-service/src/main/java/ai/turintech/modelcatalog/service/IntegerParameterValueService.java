package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.IntegerParameterValueCallable;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterValueMapper;
import ai.turintech.modelcatalog.repository.IntegerParameterValueRepository;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
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
 * Service Implementation for managing {@link IntegerParameterValue}.
 */
@Service
@Transactional
public class IntegerParameterValueService {

    private final Logger log = LoggerFactory.getLogger(IntegerParameterValueService.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private IntegerParameterValueRepository integerParameterValueRepository;
    @Autowired
    private IntegerParameterValueMapper integerParameterValueMapper;

    /**
     * Save a integerParameterValue.
     *
     * @param integerParameterValueDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<IntegerParameterValueDTO> save(IntegerParameterValueDTO integerParameterValueDTO) {
        log.debug("Request to save IntegerParameterValue : {}", integerParameterValueDTO);
        Callable<IntegerParameterValueDTO> callable = context.getBean(IntegerParameterValueCallable.class, "create", integerParameterValueDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a integerParameterValue.
     *
     * @param integerParameterValueDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<IntegerParameterValueDTO> update(IntegerParameterValueDTO integerParameterValueDTO) {
        log.debug("Request to update IntegerParameterValue : {}", integerParameterValueDTO);
        Callable<IntegerParameterValueDTO> callable = context.getBean(IntegerParameterValueCallable.class, "update", integerParameterValueDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a integerParameterValue.
     *
     * @param integerParameterValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<IntegerParameterValueDTO> partialUpdate(IntegerParameterValueDTO integerParameterValueDTO) {
        log.debug("Request to partially update IntegerParameterValue : {}", integerParameterValueDTO);
        Callable<IntegerParameterValueDTO> callable = context.getBean(IntegerParameterValueCallable.class, "partialUpdate", integerParameterValueDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the integerParameterValues.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<IntegerParameterValueDTO>> findAll() {
        log.debug("Request to get all IntegerParameterValues");
        Callable<List<IntegerParameterValueDTO>> callable = context.getBean(IntegerParameterValueCallable.class, "findAll");
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<IntegerParameterValueDTO> findAllStream() {
        log.debug("Request to get all IntegerParameterValues");

        return Flux.defer(() -> Flux.fromStream(
                        integerParameterValueRepository.findAll().stream()
                                .map(integerParameterValueMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get one integerParameterValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IntegerParameterValueDTO> findOne(UUID id) {
        log.debug("Request to get IntegerParameterValue : {}", id);
        Callable<IntegerParameterValueDTO> callable = context.getBean(IntegerParameterValueCallable.class, "findById", id);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Delete the integerParameterValue by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete IntegerParameterValue : {}", id);
        Callable<Void> callable = context.getBean(IntegerParameterValueCallable.class, "delete", id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }
}
