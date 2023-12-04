package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.GenericModelCallableImpl;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterTypeMapper;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.repository.ParameterTypeRepository;
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

/** Service Implementation for managing {@link ParameterType}. */
@Service
@Transactional
public class ParameterTypeServiceImpl implements ParameterTypeService {

  private final Logger log = LoggerFactory.getLogger(ParameterTypeServiceImpl.class);

  @Autowired private ApplicationContext context;

  @Autowired private Scheduler jdbcScheduler;

  @Autowired private ParameterTypeRepository parameterTypeRepository;

  @Autowired private ParameterTypeMapper parameterTypeMapper;

  /**
   * Save a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ParameterTypeDTO> save(ParameterTypeDTO parameterTypeDTO) {
    log.debug("Request to save ParameterType : {}", parameterTypeDTO);
    GenericModelCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "create",
            parameterTypeDTO,
            parameterTypeRepository,
            parameterTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Update a parameterType.
   *
   * @param parameterTypeDTO the entity to save.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ParameterTypeDTO> update(ParameterTypeDTO parameterTypeDTO) {
    log.debug("Request to update ParameterType : {}", parameterTypeDTO);
    GenericModelCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "update",
            parameterTypeDTO,
            parameterTypeRepository,
            parameterTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Partially update a parameterType.
   *
   * @param parameterTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  @Transactional
  public Mono<ParameterTypeDTO> partialUpdate(ParameterTypeDTO parameterTypeDTO) {
    log.debug("Request to partially update ParameterType : {}", parameterTypeDTO);
    GenericModelCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "partialUpdate",
            parameterTypeDTO.getId(),
            parameterTypeDTO,
            parameterTypeRepository,
            parameterTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Get all the parameterTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Mono<List<ParameterTypeDTO>> findAll() {
    log.debug("Request to get all ParameterTypes");
    GenericModelCallableImpl<List<ParameterTypeDTO>, ParameterTypeDTO, ParameterType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "findAll",
            parameterTypeRepository,
            parameterTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional(readOnly = true)
  public Flux<ParameterTypeDTO> findAllStream() {
    log.debug("Request to get all ParameterTypes");

    return Flux.defer(
            () ->
                Flux.fromStream(
                    parameterTypeRepository.findAll().stream().map(parameterTypeMapper::to)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  /**
   * Get one parameterType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Mono<ParameterTypeDTO> findOne(UUID id) {
    log.debug("Request to get ParameterType : {}", id);
    GenericModelCallableImpl<ParameterTypeDTO, ParameterTypeDTO, ParameterType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "findById",
            id,
            parameterTypeRepository,
            parameterTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  /**
   * Delete the parameterType by id.
   *
   * @param id the id of the entity.
   */
  @Transactional
  public Mono<Void> delete(UUID id) {
    log.debug("Request to delete ParameterType : {}", id);
    GenericModelCallableImpl<Void, ParameterTypeDTO, ParameterType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "delete",
            id,
            parameterTypeRepository,
            parameterTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }

  @Transactional
  public Mono<Boolean> existsById(UUID id) {
    log.debug("Request to check if ModelGroupType exists : {}", id);
    GenericModelCallableImpl<Boolean, ParameterTypeDTO, ParameterType> callable =
        context.getBean(
            GenericModelCallableImpl.class,
            "existsById",
            id,
            parameterTypeRepository,
            parameterTypeMapper);
    return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
  }
}
