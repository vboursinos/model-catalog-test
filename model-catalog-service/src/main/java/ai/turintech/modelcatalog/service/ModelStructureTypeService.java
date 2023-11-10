package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.ModelStructureTypeCallable;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelStructureTypeMapper;
import ai.turintech.modelcatalog.repository.ModelStructureTypeRepository;
import ai.turintech.modelcatalog.entity.ModelStructureType;
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
 * Service Implementation for managing {@link ModelStructureType}.
 */
@Service
@Transactional
public class ModelStructureTypeService {

    private final Logger log = LoggerFactory.getLogger(ModelStructureTypeService.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private ModelStructureTypeRepository modelStructureTypeRepository;

    @Autowired
    private ModelStructureTypeMapper modelStructureTypeMapper;

    /**
     * Save a modelStructureType.
     *
     * @param modelStructureTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelStructureTypeDTO> save(ModelStructureTypeDTO modelStructureTypeDTO) {
        log.debug("Request to save ModelStructureType : {}", modelStructureTypeDTO);
        Callable<ModelStructureTypeDTO> modelStructureTypeCallable = context.getBean(ModelStructureTypeCallable.class, "create", modelStructureTypeDTO);
        return Mono.fromCallable(modelStructureTypeCallable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Update a modelStructureType.
     *
     * @param modelStructureTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelStructureTypeDTO> update(ModelStructureTypeDTO modelStructureTypeDTO) {
        log.debug("Request to update ModelStructureType : {}", modelStructureTypeDTO);
        Callable<ModelStructureTypeDTO> modelStructureTypeCallable = context.getBean(ModelStructureTypeCallable.class, "update", modelStructureTypeDTO);
        return Mono.fromCallable(modelStructureTypeCallable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Partially update a modelStructureType.
     *
     * @param modelStructureTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelStructureTypeDTO> partialUpdate(ModelStructureTypeDTO modelStructureTypeDTO) {
        log.debug("Request to partially update ModelStructureType : {}", modelStructureTypeDTO);
        Callable<ModelStructureTypeDTO> modelStructureTypeCallable = context.getBean(ModelStructureTypeCallable.class, "partialUpdate", modelStructureTypeDTO);
        return Mono.fromCallable(modelStructureTypeCallable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Get all the modelStructureTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<ModelStructureTypeDTO>> findAll() {
        log.debug("Request to get all ModelStructureTypes");
        Callable<List<ModelStructureTypeDTO>> modelStructureTypeCallable = context.getBean(ModelStructureTypeCallable.class, "findAll");
        return Mono.fromCallable(modelStructureTypeCallable)
                .subscribeOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<ModelStructureTypeDTO> findAllStream() {
        log.debug("Request to get all ModelStructureTypes");

        return Flux.defer(() -> Flux.fromStream(
                        modelStructureTypeRepository.findAll().stream()
                                .map(modelStructureTypeMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }
    /**
     * Get one modelStructureType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ModelStructureTypeDTO> findOne(UUID id) {
        log.debug("Request to get ModelStructureType : {}", id);
        Callable<ModelStructureTypeDTO> modelStructureTypeCallable = context.getBean(ModelStructureTypeCallable.class, "findById", id);
        return Mono.fromCallable(modelStructureTypeCallable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the modelStructureType by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete ModelStructureType : {}", id);
        Callable<Void> callable = context.getBean(ModelStructureTypeCallable.class, "delete", id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        return Mono.just(modelStructureTypeRepository.existsById(id));
    }
}
