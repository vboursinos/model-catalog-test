package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelStructureTypeMapper;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import ai.turintech.modelcatalog.repository.ModelStructureTypeRepository;
import java.util.List;
import java.util.UUID;
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

/** Service Implementation for managing {@link ModelStructureType}. */
@Service
@Transactional
public class ModelStructureTypeService {

  private final Logger log = LoggerFactory.getLogger(ModelStructureTypeService.class);

  @Autowired private ApplicationContext context;
  @Autowired private Scheduler jdbcScheduler;
  @Autowired private ModelStructureTypeRepository modelStructureTypeRepository;

  @Autowired private ModelStructureTypeMapper modelStructureTypeMapper;

  /**
   * Save a modelStructureType.
   *
   * @param modelStructureTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelStructureTypeDTO> save(ModelStructureTypeDTO modelStructureTypeDTO) {
    log.debug("Request to save ModelStructureType : {}", modelStructureTypeDTO);
    GenericModelCallable<ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType> callable =
        context.getBean(
                GenericModelCallable.class,
            "create",
            modelStructureTypeDTO,
            modelStructureTypeRepository,
            modelStructureTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
    GenericModelCallable<ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType> callable =
        context.getBean(
                GenericModelCallable.class,
            "update",
            modelStructureTypeDTO,
            modelStructureTypeRepository,
            modelStructureTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
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
    GenericModelCallable<ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType> callable =
        context.getBean(
                GenericModelCallable.class,
            "partialUpdate",
            modelStructureTypeDTO.getId(),
            modelStructureTypeDTO,
            modelStructureTypeRepository,
            modelStructureTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the modelStructureTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ModelStructureTypeDTO>> findAll() {
    log.debug("Request to get all ModelStructureTypes");
    GenericModelCallable<List<ModelStructureTypeDTO>, ModelStructureTypeDTO, ModelStructureType>
        callable =
            context.getBean(
                    GenericModelCallable.class,
                "findAll",
                modelStructureTypeRepository,
                modelStructureTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<ModelStructureTypeDTO> findAllStream() {
    log.debug("Request to get all ModelStructureTypes");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    modelStructureTypeRepository.findAll().stream()
                        .map(modelStructureTypeMapper::to)))
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
    GenericModelCallable<ModelStructureTypeDTO, ModelStructureTypeDTO, ModelStructureType> callable =
        context.getBean(
                GenericModelCallable.class,
            "findById",
            id,
            modelStructureTypeRepository,
            modelStructureTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the modelStructureType by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ModelStructureType : {}", id);
    GenericModelCallable<Void, ModelStructureTypeDTO, ModelStructureType> callable =
        context.getBean(
                GenericModelCallable.class,
            "delete",
            id,
            modelStructureTypeRepository,
            modelStructureTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, ModelStructureTypeDTO, ModelStructureType> callable =
        context.getBean(
                GenericModelCallable.class,
            "existsById",
            id,
            modelStructureTypeRepository,
            modelStructureTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
