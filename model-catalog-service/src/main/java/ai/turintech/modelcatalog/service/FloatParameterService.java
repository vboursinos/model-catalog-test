package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterMapper;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.repository.FloatParameterRepository;
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
        GenericCallable<FloatParameterDTO, FloatParameterDTO, FloatParameter> callable = context.getBean(GenericCallable.class, "create", floatParameterDTO, floatParameterRepository, floatParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
        GenericCallable<FloatParameterDTO, FloatParameterDTO, FloatParameter> callable = context.getBean(GenericCallable.class, "update", floatParameterDTO, floatParameterRepository, floatParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
        GenericCallable<FloatParameterDTO, FloatParameterDTO, FloatParameter> callable = context.getBean(GenericCallable.class, "partialUpdate", floatParameterDTO, floatParameterRepository, floatParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Get all the floatParameters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<FloatParameterDTO>> findAll() {
        log.debug("Request to get all FloatParameters");
        GenericCallable<List<FloatParameterDTO>, FloatParameterDTO, FloatParameter> callable = context.getBean(GenericCallable.class, "findAAll", floatParameterRepository, floatParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<FloatParameterDTO> findAllStream() {
        log.debug("Request to get all FloatParameters");
        return Flux.fromStream(floatParameterRepository.findAll().stream().map(floatParameterMapper::toDto)).subscribeOn(Schedulers.boundedElastic());
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
        GenericCallable<FloatParameterDTO, FloatParameterDTO, FloatParameter> callable = context.getBean(GenericCallable.class, "findById", id, floatParameterRepository, floatParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the floatParameter by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete FloatParameter : {}", id);
        GenericCallable<Void, FloatParameterDTO, FloatParameter> callable = context.getBean(GenericCallable.class, "delete", id, floatParameterRepository, floatParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        GenericCallable<Boolean, FloatParameterDTO, FloatParameter> callable = context.getBean(GenericCallable.class, "existsById", id, floatParameterRepository, floatParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }
}
