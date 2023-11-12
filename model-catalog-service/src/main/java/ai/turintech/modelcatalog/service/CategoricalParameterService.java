package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.CategoricalParameterMapper;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.repository.CategoricalParameterRepository;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
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
 * Service Implementation for managing {@link CategoricalParameter}.
 */
@Service
@Transactional
public class CategoricalParameterService {

    private final Logger log = LoggerFactory.getLogger(CategoricalParameterService.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private CategoricalParameterRepository categoricalParameterRepository;

    @Autowired
    private CategoricalParameterMapper categoricalParameterMapper;

    /**
     * Save a categoricalParameter.
     *
     * @param categoricalParameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<CategoricalParameterDTO> save(CategoricalParameterDTO categoricalParameterDTO) {
        log.debug("Request to save CategoricalParameter : {}", categoricalParameterDTO);
        GenericCallable<CategoricalParameterDTO, CategoricalParameterDTO, CategoricalParameter> callable = context.getBean(GenericCallable.class, "create", categoricalParameterDTO, categoricalParameterRepository, categoricalParameterMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a categoricalParameter.
     *
     * @param categoricalParameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<CategoricalParameterDTO> update(CategoricalParameterDTO categoricalParameterDTO) {
        log.debug("Request to update CategoricalParameter : {}", categoricalParameterDTO);
        GenericCallable<CategoricalParameterDTO, CategoricalParameterDTO, CategoricalParameter> callable = context.getBean(GenericCallable.class, "update", categoricalParameterDTO, categoricalParameterRepository, categoricalParameterMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a categoricalParameter.
     *
     * @param categoricalParameterDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<CategoricalParameterDTO> partialUpdate(CategoricalParameterDTO categoricalParameterDTO) {
        log.debug("Request to partially update CategoricalParameter : {}", categoricalParameterDTO);
        GenericCallable<CategoricalParameterDTO, CategoricalParameterDTO, CategoricalParameter> callable = context.getBean(GenericCallable.class, "partialUpdate", categoricalParameterDTO, categoricalParameterRepository, categoricalParameterMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the categoricalParameters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<CategoricalParameterDTO>> findAll() {
        log.debug("Request to get all CategoricalParameters");
        GenericCallable<List<CategoricalParameterDTO>, CategoricalParameterDTO, CategoricalParameter> callable = context.getBean(GenericCallable.class, "findAll", categoricalParameterRepository, categoricalParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<CategoricalParameterDTO> findAllStream() {
        log.debug("Request to get all CategoricalParameters");

        return Flux.defer(() -> Flux.fromStream(
                        categoricalParameterRepository.findAll().stream()
                                .map(categoricalParameterMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }
    /**
     * Get one categoricalParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<CategoricalParameterDTO> findOne(UUID id) {
        log.debug("Request to get CategoricalParameter : {}", id);
        GenericCallable<CategoricalParameterDTO, CategoricalParameterDTO, CategoricalParameter> callable = context.getBean(GenericCallable.class, "findById", id, categoricalParameterRepository, categoricalParameterMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the categoricalParameter by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete CategoricalParameter : {}", id);
        GenericCallable<Void, CategoricalParameterDTO, CategoricalParameter> callable = context.getBean(GenericCallable.class, "delete", id, categoricalParameterRepository, categoricalParameterMapper);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        return Mono.just(categoricalParameterRepository.existsById(id));
    }
}
