package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterDistributionTypeMapper;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.repository.ParameterDistributionTypeRepository;
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
 * Service Implementation for managing {@link ParameterDistributionType}.
 */
@Service
@Transactional
public class ParameterDistributionTypeService {

    private final Logger log = LoggerFactory.getLogger(ParameterDistributionTypeService.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Scheduler jdbcScheduler;

    @Autowired
    private ParameterDistributionTypeRepository parameterDistributionTypeRepository;

    @Autowired
    private ParameterDistributionTypeMapper parameterDistributionTypeMapper;

    /**
     * Save a parameterDistributionType.
     *
     * @param parameterDistributionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ParameterDistributionTypeDTO> save(ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
        log.debug("Request to save ParameterDistributionType : {}", parameterDistributionTypeDTO);
        GenericCallable<ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType> callable = context.getBean(GenericCallable.class, "create", parameterDistributionTypeDTO, parameterDistributionTypeRepository, parameterDistributionTypeMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Update a parameterDistributionType.
     *
     * @param parameterDistributionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ParameterDistributionTypeDTO> update(ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
        log.debug("Request to update ParameterDistributionType : {}", parameterDistributionTypeDTO);
        GenericCallable<ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType> callable = context.getBean(GenericCallable.class, "update", parameterDistributionTypeDTO, parameterDistributionTypeRepository, parameterDistributionTypeMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Partially update a parameterDistributionType.
     *
     * @param parameterDistributionTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ParameterDistributionTypeDTO> partialUpdate(ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
        log.debug("Request to partially update ParameterDistributionType : {}", parameterDistributionTypeDTO);
        GenericCallable<ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType> callable = context.getBean(GenericCallable.class, "partialUpdate", parameterDistributionTypeDTO, parameterDistributionTypeRepository, parameterDistributionTypeMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Get all the parameterDistributionTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<ParameterDistributionTypeDTO>> findAll() {
        log.debug("Request to get all ParameterDistributionTypes");
        GenericCallable<List<ParameterDistributionTypeDTO>, ParameterDistributionTypeDTO, ParameterDistributionType> callable = context.getBean(GenericCallable.class, "findAll", parameterDistributionTypeRepository, parameterDistributionTypeMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    @Transactional(readOnly = true)
    public Flux<ParameterDistributionTypeDTO> findAllStream() {
        log.debug("Request to get all ParameterDistributionTypes");

        return Flux.defer(() -> Flux.fromStream(
                        parameterDistributionTypeRepository.findAll().stream()
                                .map(parameterDistributionTypeMapper::toDto)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get one parameterDistributionType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ParameterDistributionTypeDTO> findOne(UUID id) {
        log.debug("Request to get ParameterDistributionType : {}", id);
        GenericCallable<ParameterDistributionTypeDTO, ParameterDistributionTypeDTO, ParameterDistributionType> callable = context.getBean(GenericCallable.class, "findById", id, parameterDistributionTypeRepository, parameterDistributionTypeMapper);
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the parameterDistributionType by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete ParameterDistributionType : {}", id);
        GenericCallable<Void, ParameterDistributionTypeDTO, ParameterDistributionType> callable = context.getBean(GenericCallable.class, "delete", id, parameterDistributionTypeRepository, parameterDistributionTypeMapper);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }


    @Transactional
    public Mono<Boolean> existsById(UUID id) {
        log.debug("Request to check if ModelGroupType exists : {}", id);
        GenericCallable<Boolean, ParameterDistributionTypeDTO, ParameterDistributionType> callable = context.getBean(GenericCallable.class, "existsById", id, parameterDistributionTypeRepository, parameterDistributionTypeMapper);
        return Mono.fromCallable(callable)
                .publishOn(jdbcScheduler);
    }
}
