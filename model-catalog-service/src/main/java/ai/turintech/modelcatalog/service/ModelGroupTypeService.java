package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelGroupTypeMapper;
import ai.turintech.modelcatalog.entity.ModelGroupType;
import ai.turintech.modelcatalog.repository.ModelGroupTypeRepository;
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

/** Service Implementation for managing {@link ModelGroupType}. */
@Service
@Transactional
public class ModelGroupTypeService {

  private final Logger log = LoggerFactory.getLogger(ModelGroupTypeService.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;

  @Autowired private ModelGroupTypeRepository modelGroupTypeRepository;

  @Autowired private ModelGroupTypeMapper modelGroupTypeMapper;

  /**
   * Save a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelGroupTypeDTO> save(ModelGroupTypeDTO modelGroupTypeDTO) {
    log.debug("Request to save ModelGroupType : {}", modelGroupTypeDTO);
    GenericModelCallable<ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType> callable =
        context.getBean(
                GenericModelCallable.class,
            "create",
            modelGroupTypeDTO,
            modelGroupTypeRepository,
            modelGroupTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelGroupTypeDTO> update(ModelGroupTypeDTO modelGroupTypeDTO) {
    log.debug("Request to update ModelGroupType : {}", modelGroupTypeDTO);
    GenericModelCallable<ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType> callable =
        context.getBean(
                GenericModelCallable.class,
            "update",
            modelGroupTypeDTO,
            modelGroupTypeRepository,
            modelGroupTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a modelGroupType.
   *
   * @param modelGroupTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelGroupTypeDTO> partialUpdate(ModelGroupTypeDTO modelGroupTypeDTO) {
    log.debug("Request to partially update ModelGroupType : {}", modelGroupTypeDTO);
    GenericModelCallable<ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType> callable =
        context.getBean(
                GenericModelCallable.class,
            "partialUpdate",
            modelGroupTypeDTO.getId(),
            modelGroupTypeDTO,
            modelGroupTypeRepository,
            modelGroupTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the modelGroupTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ModelGroupTypeDTO>> findAll() {
    log.debug("Request to get all ModelGroupTypes");
    GenericModelCallable<List<ModelGroupTypeDTO>, ModelGroupTypeDTO, ModelGroupType> callable =
        context.getBean(
                GenericModelCallable.class, "findAll", modelGroupTypeRepository, modelGroupTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<ModelGroupTypeDTO> findAllStream() {
    log.debug("Request to get all ModelGroupTypes");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    modelGroupTypeRepository.findAll().stream().map(modelGroupTypeMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Get one modelGroupType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelGroupTypeDTO> findOne(UUID id) {
    log.debug("Request to get ModelGroupType : {}", id);
    GenericModelCallable<ModelGroupTypeDTO, ModelGroupTypeDTO, ModelGroupType> callable =
        context.getBean(
                GenericModelCallable.class, "findById", id, modelGroupTypeRepository, modelGroupTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the modelGroupType by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ModelGroupType : {}", id);
    GenericModelCallable<Void, ModelGroupTypeDTO, ModelGroupType> callable =
        context.getBean(
                GenericModelCallable.class, "delete", id, modelGroupTypeRepository, modelGroupTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, ModelGroupTypeDTO, ModelGroupType> callable =
        context.getBean(
                GenericModelCallable.class,
            "existsById",
            id,
            modelGroupTypeRepository,
            modelGroupTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
