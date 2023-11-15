package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericCallable;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.MlTaskTypeMapper;
import ai.turintech.modelcatalog.entity.MlTaskType;
import ai.turintech.modelcatalog.repository.MlTaskTypeRepository;
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

/** Service Implementation for managing {@link MlTaskType}. */
@Service
@Transactional
public class MlTaskTypeService {

  private final Logger log = LoggerFactory.getLogger(MlTaskTypeService.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;
  @Autowired private MlTaskTypeRepository mlTaskTypeRepository;

  @Autowired private MlTaskTypeMapper mlTaskTypeMapper;

  /**
   * Save a mlTaskType.
   *
   * @param mlTaskTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<MlTaskTypeDTO> save(MlTaskTypeDTO mlTaskTypeDTO) {
    log.debug("Request to save MlTaskType : {}", mlTaskTypeDTO);
    GenericCallable<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType> callable =
        context.getBean(
            GenericCallable.class, "create", mlTaskTypeDTO, mlTaskTypeRepository, mlTaskTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a mlTaskType.
   *
   * @param mlTaskTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<MlTaskTypeDTO> update(MlTaskTypeDTO mlTaskTypeDTO) {
    log.debug("Request to update MlTaskType : {}", mlTaskTypeDTO);
    GenericCallable<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType> callable =
        context.getBean(
            GenericCallable.class, "update", mlTaskTypeDTO, mlTaskTypeRepository, mlTaskTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a mlTaskType.
   *
   * @param mlTaskTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<MlTaskTypeDTO> partialUpdate(MlTaskTypeDTO mlTaskTypeDTO) {
    log.debug("Request to partially update MlTaskType : {}", mlTaskTypeDTO);
    GenericCallable<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType> callable =
        context.getBean(
            GenericCallable.class,
            "partialUpdate",
            mlTaskTypeDTO,
            mlTaskTypeRepository,
            mlTaskTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the mlTaskTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<MlTaskTypeDTO>> findAll() {
    log.debug("Request to get all MlTaskTypes");
    GenericCallable<List<MlTaskTypeDTO>, MlTaskTypeDTO, MlTaskType> callable =
        context.getBean(GenericCallable.class, "findAll", mlTaskTypeRepository, mlTaskTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<MlTaskTypeDTO> findAllStream() {
    log.debug("Request to get all MlTaskTypes");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    mlTaskTypeRepository.findAll().stream().map(mlTaskTypeMapper::toDto)))
        .subscribeOn(Schedulers.boundedElastic());
  }
  /**
   * Get one mlTaskType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<MlTaskTypeDTO> findOne(UUID id) {
    log.debug("Request to get MlTaskType : {}", id);
    GenericCallable<MlTaskTypeDTO, MlTaskTypeDTO, MlTaskType> callable =
        context.getBean(
            GenericCallable.class, "findById", id, mlTaskTypeRepository, mlTaskTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the mlTaskType by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete MlTaskType : {}", id);
    GenericCallable<Void, MlTaskTypeDTO, MlTaskType> callable =
        context.getBean(
            GenericCallable.class, "create", id, mlTaskTypeRepository, mlTaskTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericCallable<Boolean, MlTaskTypeDTO, MlTaskType> callable =
        context.getBean(
            GenericCallable.class, "existsById", id, mlTaskTypeRepository, mlTaskTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
