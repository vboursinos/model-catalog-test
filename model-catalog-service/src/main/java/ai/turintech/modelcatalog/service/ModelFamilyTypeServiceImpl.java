package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallable;
import ai.turintech.modelcatalog.callable.GenericModelCallableImpl;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelFamilyTypeMapper;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import ai.turintech.modelcatalog.repository.ModelFamilyTypeRepository;
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

/** Service Implementation for managing {@link ModelFamilyType}. */
@Service
@Transactional
public class ModelFamilyTypeServiceImpl implements ModelFamilyTypeService {

  private final Logger log = LoggerFactory.getLogger(ModelFamilyTypeServiceImpl.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;
  @Autowired private ModelFamilyTypeRepository modelFamilyTypeRepository;

  @Autowired private ModelFamilyTypeMapper modelFamilyTypeMapper;

  /**
   * Save a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelFamilyTypeDTO> save(ModelFamilyTypeDTO modelFamilyTypeDTO) {
    log.debug("Request to save ModelFamilyType : {}", modelFamilyTypeDTO);
    GenericModelCallable<ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "create",
            modelFamilyTypeDTO,
            modelFamilyTypeRepository,
            modelFamilyTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelFamilyTypeDTO> update(ModelFamilyTypeDTO modelFamilyTypeDTO) {
    log.debug("Request to update ModelFamilyType : {}", modelFamilyTypeDTO);
    GenericModelCallable<ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "update",
            modelFamilyTypeDTO,
            modelFamilyTypeRepository,
            modelFamilyTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a modelFamilyType.
   *
   * @param modelFamilyTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ModelFamilyTypeDTO> partialUpdate(ModelFamilyTypeDTO modelFamilyTypeDTO) {
    log.debug("Request to partially update ModelFamilyType : {}", modelFamilyTypeDTO);
    GenericModelCallable<ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "partialUpdate",
            modelFamilyTypeDTO.getId(),
            modelFamilyTypeDTO,
            modelFamilyTypeRepository,
            modelFamilyTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the modelFamilyTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ModelFamilyTypeDTO>> findAll() {
    log.debug("Request to get all ModelFamilyTypes");
    GenericModelCallable<List<ModelFamilyTypeDTO>, ModelFamilyTypeDTO, ModelFamilyType>
        callable =
            context.getBean(
                GenericModelCallableImpl.class,
                "findAll",
                modelFamilyTypeRepository,
                modelFamilyTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<ModelFamilyTypeDTO> findAllStream() {
    log.debug("Request to get all ModelFamilyTypes");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    modelFamilyTypeRepository.findAll().stream().map(modelFamilyTypeMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Get one modelFamilyType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ModelFamilyTypeDTO> findOne(UUID id) {
    log.debug("Request to get ModelFamilyType : {}", id);
    GenericModelCallable<ModelFamilyTypeDTO, ModelFamilyTypeDTO, ModelFamilyType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "findById",
            id,
            modelFamilyTypeRepository,
            modelFamilyTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the modelFamilyType by id.
   *
   * @param id the id of the entity.
   */
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ModelFamilyType : {}", id);
    GenericModelCallable<Void, ModelFamilyTypeDTO, ModelFamilyType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "delete",
            id,
            modelFamilyTypeRepository,
            modelFamilyTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallable<Boolean, ModelFamilyTypeDTO, ModelFamilyType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "existsById",
            id,
            modelFamilyTypeRepository,
            modelFamilyTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
