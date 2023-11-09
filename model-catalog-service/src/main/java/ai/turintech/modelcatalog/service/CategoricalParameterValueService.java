package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.CategoricalParameterValueCallable;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.dtoentitymapper.CategoricalParameterValueMapper;
import ai.turintech.modelcatalog.repository.CategoricalParameterValueRepository;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
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
 * Service Implementation for managing {@link CategoricalParameterValue}.
 */
@Service
@Transactional
public class CategoricalParameterValueService {

    private final Logger log = LoggerFactory.getLogger(CategoricalParameterValueService.class);
    @Autowired
    private ApplicationContext context;
    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private CategoricalParameterValueRepository categoricalParameterValueRepository;
    @Autowired
    private CategoricalParameterValueMapper categoricalParameterValueMapper;


    /**
     * Save a categoricalParameterValue.
     *
     * @param categoricalParameterValueDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<CategoricalParameterValueDTO> save(CategoricalParameterValueDTO categoricalParameterValueDTO) {
        log.debug("Request to save CategoricalParameterValue : {}", categoricalParameterValueDTO);
        Callable<CategoricalParameterValueDTO> callable = context.getBean(CategoricalParameterValueCallable.class, "create", categoricalParameterValueDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a categoricalParameterValue.
     *
     * @param categoricalParameterValueDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<CategoricalParameterValueDTO> update(CategoricalParameterValueDTO categoricalParameterValueDTO) {
        log.debug("Request to update CategoricalParameterValue : {}", categoricalParameterValueDTO);
        Callable<CategoricalParameterValueDTO> callable = context.getBean(CategoricalParameterValueCallable.class, "update", categoricalParameterValueDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a categoricalParameterValue.
     *
     * @param categoricalParameterValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<CategoricalParameterValueDTO> partialUpdate(CategoricalParameterValueDTO categoricalParameterValueDTO) {
        log.debug("Request to partially update CategoricalParameterValue : {}", categoricalParameterValueDTO);
        Callable<CategoricalParameterValueDTO> callable = context.getBean(CategoricalParameterValueCallable.class, "partialUpdate", categoricalParameterValueDTO);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the categoricalParameterValues.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<CategoricalParameterValueDTO>> findAll() {
        log.debug("Request to get all CategoricalParameterValues");
        Callable<List<CategoricalParameterValueDTO>> callable = context.getBean(CategoricalParameterValueCallable.class, "findAll");
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<CategoricalParameterValueDTO> findAllStream() {
        log.debug("Request to get all CategoricalParameterValues");

        return Flux.defer(() -> Flux.fromStream(
                        categoricalParameterValueRepository.findAll().stream()
                                .map(categoricalParameterValueMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get one categoricalParameterValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<CategoricalParameterValueDTO> findOne(UUID id) {
        log.debug("Request to get CategoricalParameterValue : {}", id);
        Callable<CategoricalParameterValueDTO> callable = context.getBean(CategoricalParameterValueCallable.class, "findById", id);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Delete the categoricalParameterValue by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete CategoricalParameterValue : {}", id);
        Callable<Void> callable = context.getBean(CategoricalParameterValueCallable.class, "delete", id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }
}
