package ai.turintech.modelcatalog.service;

import ai.turintech.components.jpa.search.service.AbstractSearchService;
import ai.turintech.components.jpa.search.service.SearchEngine;
import ai.turintech.components.jpa.search.service.SearchService;
import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.callable.ModelCallable;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelMapper;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.exceptions.FindOneException;
import ai.turintech.modelcatalog.repository.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Service Implementation for managing {@link Model}.
 */
@Service
@Transactional
public class ModelService extends AbstractSearchService<Model,ModelDTO> implements SearchService<ModelDTO> {

    private final ApplicationContext context;
    private final Scheduler jdbcScheduler;
    private final Logger log = LoggerFactory.getLogger(ModelService.class);
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    private final PaginationConverter paginationConverter;
    private final Class<Model> clazzEntity;
    private final Class<ModelDTO> clazzDTO;

    public ModelService(ApplicationContext context,
                        Scheduler jdbcScheduler,
                        ModelRepository modelRepository,
                        ModelMapper modelMapper,
                        PaginationConverter paginationConverter,
                        Class<Model> clazzEntity,
                        Class<ModelDTO> clazzDTO) {

        super(clazzEntity, clazzDTO);
        this.context = context;
        this.jdbcScheduler = jdbcScheduler;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.paginationConverter = paginationConverter;
        this.clazzEntity = clazzEntity;
        this.clazzDTO = clazzDTO;
    }
    /**
     * Save a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelDTO> save(ModelDTO modelDTO) {
        log.debug("Request to save Model : {}", modelDTO);
        GenericCallable<ModelDTO, ModelDTO, Model> callable = context.getBean(GenericCallable.class, "create", modelDTO, modelRepository, modelMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Update a model.
     *
     * @param modelDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelDTO> update(ModelDTO modelDTO) {
        log.debug("Request to update Model : {}", modelDTO);
        GenericCallable<ModelDTO, ModelDTO, Model> callable = context.getBean(GenericCallable.class, "update", modelDTO, modelRepository, modelMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Partially update a model.
     *
     * @param modelDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ModelDTO> partialUpdate(ModelDTO modelDTO) {
        log.debug("Request to partially update Model : {}", modelDTO);
        GenericCallable<ModelDTO, ModelDTO, Model> callable = context.getBean(GenericCallable.class, "partialUpdate", modelDTO, modelRepository, modelMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }


    /**
     * Get all the models.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<ModelPaginatedListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Models");
        Callable<ModelPaginatedListDTO> callable = context.getBean(ModelCallable.class, "findAll", pageable);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Get all the models with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ModelDTO> findAllWithEagerRelationships(Pageable pageable) {
        List<Model> models = modelRepository.findAllWithEagerRelationships();
        return modelRepository.findAllWithEagerRelationships(pageable).map(modelMapper::toDto);
    }

    /**
     * Get one model by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ModelDTO> findOne(UUID id) throws FindOneException {
        log.debug("Request to get Model : {}", id);
        GenericCallable<ModelDTO, ModelDTO, Model> callable = context.getBean(GenericCallable.class, "findById", id, modelRepository, modelMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the model by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete Model : {}", id);
        GenericCallable<Void, ModelDTO, Model> callable = context.getBean(GenericCallable.class, "delete", id, modelRepository, modelMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        GenericCallable<Boolean, ModelDTO, Model> callable = context.getBean(GenericCallable.class, "existsById", id, modelRepository, modelMapper);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }
}
