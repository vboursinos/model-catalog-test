package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelEnsembleTypeMapper;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import ai.turintech.modelcatalog.repository.ModelEnsembleTypeRepository;
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

/** Service Implementation for managing {@link ModelEnsembleType}. */
@Service
@Transactional
public class ModelEnsembleTypeService {

  private final Logger log = LoggerFactory.getLogger(ModelEnsembleTypeService.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;

  @Autowired private ModelEnsembleTypeRepository modelEnsembleTypeRepository;

  @Autowired private ModelEnsembleTypeMapper modelEnsembleTypeMapper;

  /**
   * Save a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelEnsembleTypeDTO> save(ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
    log.debug("Request to save ModelEnsembleType : {}", modelEnsembleTypeDTO);
    GenericModelCallable<ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType> callable =
        context.getBean(
            GenericModelCallable.class,
            "create",
            modelEnsembleTypeDTO,
            modelEnsembleTypeRepository,
            modelEnsembleTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelEnsembleTypeDTO> update(ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
    log.debug("Request to update ModelEnsembleType : {}", modelEnsembleTypeDTO);
    GenericModelCallable<ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType> callable =
        context.getBean(
            GenericModelCallable.class,
            "update",
            modelEnsembleTypeDTO,
            modelEnsembleTypeRepository,
            modelEnsembleTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a modelEnsembleType.
   *
   * @param modelEnsembleTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelEnsembleTypeDTO> partialUpdate(ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
    log.debug("Request to partially update ModelEnsembleType : {}", modelEnsembleTypeDTO);
    GenericModelCallable<ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType> callable =
        context.getBean(
            GenericModelCallable.class,
            "partialUpdate",
            modelEnsembleTypeDTO.getId(),
            modelEnsembleTypeDTO,
            modelEnsembleTypeRepository,
            modelEnsembleTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the modelEnsembleTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ModelEnsembleTypeDTO>> findAll() {
    log.debug("Request to get all ModelEnsembleTypes");
    GenericModelCallable<List<ModelEnsembleTypeDTO>, ModelEnsembleTypeDTO, ModelEnsembleType>
        callable =
            context.getBean(
                GenericModelCallable.class,
                "findAll",
                modelEnsembleTypeRepository,
                modelEnsembleTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<ModelEnsembleTypeDTO> findAllStream() {
    log.debug("Request to get all ModelEnsembleTypes");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    modelEnsembleTypeRepository.findAll().stream()
                        .map(modelEnsembleTypeMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }
  /**
   * Get one modelEnsembleType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelEnsembleTypeDTO> findOne(UUID id) {
    log.debug("Request to get ModelEnsembleType : {}", id);
    GenericModelCallable<ModelEnsembleTypeDTO, ModelEnsembleTypeDTO, ModelEnsembleType> callable =
        context.getBean(
            GenericModelCallable.class,
            "findById",
            id,
            modelEnsembleTypeRepository,
            modelEnsembleTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the modelEnsembleType by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ModelEnsembleType : {}", id);
    GenericModelCallable<Void, ModelEnsembleTypeDTO, ModelEnsembleType> callable =
        context.getBean(
            GenericModelCallable.class,
            "delete",
            id,
            modelEnsembleTypeRepository,
            modelEnsembleTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, ModelEnsembleTypeDTO, ModelEnsembleType> callable =
        context.getBean(
            GenericModelCallable.class,
            "existsById",
            id,
            modelEnsembleTypeRepository,
            modelEnsembleTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
