package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.FloatParameterCallable;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterMapper;
import ai.turintech.modelcatalog.repository.FloatParameterRepository;
import ai.turintech.modelcatalog.entity.FloatParameter;
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
 * Service Implementation for managing {@link FloatParameter}.
 */
@Service
@Transactional
public class FloatParameterService {

    private final Logger log = LoggerFactory.getLogger(FloatParameterService.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Scheduler jdbcScheduler;

    @Autowired
    private FloatParameterRepository floatParameterRepository;

    @Autowired
    private FloatParameterMapper floatParameterMapper;

    /**
     * Save a floatParameter.
     *
     * @param floatParameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<FloatParameterDTO> save(FloatParameterDTO floatParameterDTO) {
        log.debug("Request to save FloatParameter : {}", floatParameterDTO);
        Callable<FloatParameterDTO> callable = context.getBean(FloatParameterCallable.class, "create", floatParameterDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a floatParameter.
     *
     * @param floatParameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<FloatParameterDTO> update(FloatParameterDTO floatParameterDTO) {
        log.debug("Request to update FloatParameter : {}", floatParameterDTO);
        Callable<FloatParameterDTO> callable = context.getBean(FloatParameterCallable.class, "update", floatParameterDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a floatParameter.
     *
     * @param floatParameterDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<FloatParameterDTO> partialUpdate(FloatParameterDTO floatParameterDTO) {
        log.debug("Request to partially update FloatParameter : {}", floatParameterDTO);
        Callable<FloatParameterDTO> callable = context.getBean(FloatParameterCallable.class, "partialUpdate", floatParameterDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the floatParameters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<FloatParameterDTO>> findAll() {
        log.debug("Request to get all FloatParameters");
        Callable<List<FloatParameterDTO>> callable = context.getBean(FloatParameterCallable.class, "findAll");
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<FloatParameterDTO> findAllStream() {
        log.debug("Request to get all FloatParameters");

        return Flux.defer(() -> Flux.fromStream(
                        floatParameterRepository.findAll().stream()
                                .map(floatParameterMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get one floatParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<FloatParameterDTO> findOne(UUID id) {
        log.debug("Request to get FloatParameter : {}", id);
        Callable<FloatParameterDTO> callable = context.getBean(FloatParameterCallable.class, "findById", id);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Delete the floatParameter by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete FloatParameter : {}", id);
        Callable<FloatParameterDTO> callable = context.getBean(FloatParameterCallable.class,"delete", id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        return Mono.just(floatParameterRepository.existsById(id));
    }
}
