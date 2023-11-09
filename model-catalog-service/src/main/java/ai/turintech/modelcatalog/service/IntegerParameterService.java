package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.IntegerParameterCallable;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterMapper;
import ai.turintech.modelcatalog.repository.IntegerParameterRepository;
import ai.turintech.modelcatalog.entity.IntegerParameter;
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
 * Service Implementation for managing {@link IntegerParameter}.
 */
@Service
@Transactional
public class IntegerParameterService {

    private final Logger log = LoggerFactory.getLogger(IntegerParameterService.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Scheduler jdbcScheduler;

    @Autowired
    private IntegerParameterRepository integerParameterRepository;

    @Autowired
    private IntegerParameterMapper integerParameterMapper;


    /**
     * Save a integerParameter.
     *
     * @param integerParameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<IntegerParameterDTO> save(IntegerParameterDTO integerParameterDTO) {
        log.debug("Request to save IntegerParameter : {}", integerParameterDTO);
        Callable<IntegerParameterDTO> callable = context.getBean(IntegerParameterCallable.class, "create", integerParameterDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a integerParameter.
     *
     * @param integerParameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<IntegerParameterDTO> update(IntegerParameterDTO integerParameterDTO) {
        log.debug("Request to update IntegerParameter : {}", integerParameterDTO);
        Callable<IntegerParameterDTO> callable = context.getBean(IntegerParameterCallable.class, "update", integerParameterDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a integerParameter.
     *
     * @param integerParameterDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<IntegerParameterDTO> partialUpdate(IntegerParameterDTO integerParameterDTO) {
        log.debug("Request to partially update IntegerParameter : {}", integerParameterDTO);
        Callable<IntegerParameterDTO> callable = context.getBean(IntegerParameterCallable.class, "partialUpdate", integerParameterDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the integerParameters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<IntegerParameterDTO>> findAll() {
        log.debug("Request to get all IntegerParameters");
        Callable<List<IntegerParameterDTO>> callable = context.getBean(IntegerParameterCallable.class, "findAll");
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<IntegerParameterDTO> findAllStream() {
        log.debug("Request to get all IntegerParameters");

        return Flux.defer(() -> Flux.fromStream(
                        integerParameterRepository.findAll().stream()
                                .map(integerParameterMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }


    /**
     * Get one integerParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<IntegerParameterDTO> findOne(UUID id) {
        log.debug("Request to get IntegerParameter : {}", id);
        Callable<IntegerParameterDTO> callable = context.getBean(IntegerParameterCallable.class, "findById", id);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Delete the integerParameter by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete IntegerParameter : {}", id);
        Callable<Void> callable = context.getBean(IntegerParameterCallable.class, "delete", id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }
}
