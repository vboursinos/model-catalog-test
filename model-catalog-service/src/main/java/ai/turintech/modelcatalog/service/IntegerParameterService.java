package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterMapper;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.repository.IntegerParameterRepository;
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
        GenericCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable = context.getBean(GenericCallable.class, "create", integerParameterDTO, integerParameterRepository, integerParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
        GenericCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable = context.getBean(GenericCallable.class, "update", integerParameterDTO, integerParameterRepository, integerParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
        GenericCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable = context.getBean(GenericCallable.class, "partialUpdate", integerParameterDTO, integerParameterRepository, integerParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Get all the integerParameters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<IntegerParameterDTO>> findAll() {
        log.debug("Request to get all IntegerParameters");
        GenericCallable<List<IntegerParameterDTO>, IntegerParameterDTO, IntegerParameter> callable = context.getBean(GenericCallable.class, "findAll", integerParameterRepository, integerParameterMapper);
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
        GenericCallable<IntegerParameterDTO, IntegerParameterDTO, IntegerParameter> callable = context.getBean(GenericCallable.class, "findById", id, integerParameterRepository, integerParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the integerParameter by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete IntegerParameter : {}", id);
        GenericCallable<Void, IntegerParameterDTO, IntegerParameter> callable = context.getBean(GenericCallable.class, "delete", id, integerParameterRepository, integerParameterMapper);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        GenericCallable<Boolean, IntegerParameterDTO, IntegerParameter> callable = context.getBean(GenericCallable.class, "existsById", id, integerParameterRepository, integerParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }
}
