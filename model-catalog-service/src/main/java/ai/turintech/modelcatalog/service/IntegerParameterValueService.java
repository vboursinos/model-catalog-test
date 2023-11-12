package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterValueMapper;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import ai.turintech.modelcatalog.repository.IntegerParameterValueRepository;
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
        GenericCallable<IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue> callable = context.getBean(GenericCallable.class, "create", integerParameterValueDTO, integerParameterValueRepository, integerParameterValueMapper);
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
        GenericCallable<IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue> callable = context.getBean(GenericCallable.class, "update", integerParameterValueDTO, integerParameterValueRepository, integerParameterValueMapper);
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
        GenericCallable<IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue> callable = context.getBean(GenericCallable.class, "partialUpdate", integerParameterValueDTO, integerParameterValueRepository, integerParameterValueMapper);
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
        GenericCallable<List<IntegerParameterValueDTO>, IntegerParameterValueDTO, IntegerParameterValue> callable = context.getBean(GenericCallable.class, "findAll", integerParameterValueRepository, integerParameterValueMapper);
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
        GenericCallable<IntegerParameterValueDTO, IntegerParameterValueDTO, IntegerParameterValue> callable = context.getBean(GenericCallable.class, "findById", id, integerParameterValueRepository, integerParameterValueMapper);
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
        GenericCallable<Void, IntegerParameterValueDTO, IntegerParameterValue> callable = context.getBean(GenericCallable.class, "delete", id, integerParameterValueRepository, integerParameterValueMapper);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        return Mono.just(integerParameterValueRepository.existsById(id));
    }
}
