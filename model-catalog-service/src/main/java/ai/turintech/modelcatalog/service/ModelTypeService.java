package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelTypeMapper;
import ai.turintech.modelcatalog.entity.ModelType;
import ai.turintech.modelcatalog.repository.ModelTypeRepository;
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
 * Service Implementation for managing {@link ModelType}.
 */
@Service
@Transactional
public class ModelTypeService {

    private final Logger log = LoggerFactory.getLogger(ModelTypeService.class);
    @Autowired
    private ApplicationContext context;

    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private ModelTypeRepository modelTypeRepository;

    @Autowired
    private ModelTypeMapper modelTypeMapper;

    /**
     * Save a modelType.
     *
     * @param modelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelTypeDTO> save(ModelTypeDTO modelTypeDTO) {
        GenericCallable<ModelTypeDTO, ModelTypeDTO, ModelType> callable = context.getBean(GenericCallable.class, "create", modelTypeDTO, modelTypeRepository, modelTypeMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Update a modelType.
     *
     * @param modelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelTypeDTO> update(ModelTypeDTO modelTypeDTO) {
        GenericCallable<ModelTypeDTO, ModelTypeDTO, ModelType> callable = context.getBean(GenericCallable.class, "update", modelTypeDTO, modelTypeRepository, modelTypeMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Partially update a modelType.
     *
     * @param modelTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelTypeDTO> partialUpdate(ModelTypeDTO modelTypeDTO) {
        log.debug("Request to partially update ModelType : {}", modelTypeDTO);
        GenericCallable<ModelTypeDTO, ModelTypeDTO, ModelType> callable = context.getBean(GenericCallable.class, "partialUpdate", modelTypeDTO, modelTypeRepository, modelTypeMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Get all the modelTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<ModelTypeDTO>> findAll() {
        log.debug("Request to get all ModelTypes");
        GenericCallable<List<ModelTypeDTO>, ModelTypeDTO, ModelType> callable = context.getBean(GenericCallable.class, "findAll", modelTypeRepository, modelTypeMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<ModelTypeDTO> findAllStream() {
        log.debug("Request to get all ModelTypes");

        return Flux.defer(() -> Flux.fromStream(
                        modelTypeRepository.findAll().stream()
                                .map(modelTypeMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get one modelType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ModelTypeDTO> findOne(UUID id) {
        log.debug("Request to get ModelType : {}", id);
        GenericCallable<ModelTypeDTO, ModelTypeDTO, ModelType> callable = context.getBean(GenericCallable.class, "findById", id, modelTypeRepository, modelTypeMapper);
        return Mono.fromCallable(callable).publishOn(jdbcScheduler);
    }

    /**
     * Delete the modelType by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete ModelType : {}", id);
        GenericCallable<Void, ModelTypeDTO, ModelType> callable = context.getBean(GenericCallable.class, "delete", id, modelTypeRepository, modelTypeMapper);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }

    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        return Mono.just(modelTypeRepository.existsById(id));
    }

}
